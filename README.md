# OPD Token Allocation Engine

## 📌 Overview
This project implements an **OPD Token Allocation Engine** for a hospital outpatient department.  
The system allocates consultation tokens to patients across fixed doctor time slots while enforcing **slot capacity**, **priority-based ordering**, and **dynamic reallocation** for real-world scenarios such as emergencies, cancellations, and no-shows.

The core logic is implemented as a **Spring Boot REST API**, with **Swagger UI** used to simulate and validate a complete OPD day workflow.

## 🎯 Problem Statement
Doctors operate in fixed OPD time slots (e.g., 9:00–10:00), each with a maximum patient capacity.  
Tokens can be generated from multiple sources:
- Online booking
- Walk-in (OPD desk)
- Paid priority patients
- Follow-up patients
- Emergency patients

The system must dynamically adapt to:
- Priority differences
- Slot capacity limits
- Emergency insertions
- Cancellations
- No-shows

## 🏗️ System Design

### Core Entities
- **Doctor** – Represents a doctor participating in OPD
- **Patient** – Represents a registered patient
- **OPDSlot** – Fixed time window for a doctor with a hard capacity limit
- **GeneratedToken** – Represents a patient’s consultation turn
- **TokenSource (Enum)** – Source of token with priority
- **TokenStatus (Enum)** – BOOKED, CANCELLED, NO_SHOW, COMPLETED

## 🔢 Token Priority Model
Priority is derived from `TokenSource` (not stored separately):

| Token Source | Priority |
|-------------|----------|
| EMERGENCY | 5 |
| PAID | 4 |
| FOLLOW_UP | 3 |
| ONLINE | 2 |
| WALK_IN | 1 |

Higher priority tokens are placed **earlier** in the consultation order.

## ⚙️ Token Allocation Algorithm (High Level)
1. Validate doctor, patient, and OPD slot
2. Ensure slot is active and belongs to the doctor
3. Enforce slot capacity (except emergency)
4. Prevent duplicate active tokens for same patient & doctor
5. Allocate token number based on priority
6. Shift existing tokens if required
7. Persist token and update slot state

All operations are **transactional** to ensure consistency.

## 🔄 Dynamic Reallocation Logic

### Emergency / High-Priority Insertion
- Emergency tokens bypass slot capacity
- Inserted at the earliest valid position
- Lower-priority tokens are shifted forward

### Cancellation
- Token status → CANCELLED
- Tokens after it shift up
- Slot capacity is freed

### No-Show
- Token status → NO_SHOW
- No shifting of other tokens
- Slot capacity is not reused (time already lost)

## 🧪 OPD Day Simulation (Using Swagger)
The complete OPD day was simulated via **Swagger UI** by executing a sequence of API calls.

### Scenario Setup
- 3 Doctors
- Multiple OPD slots per doctor
- Multiple patients
- Mixed token sources

### 🔹 Step 1: Online Bookings
POST medoc_health/setToken
Patient 1 → Doctor A → Slot 1 → ONLINE → Token 1
Patient 2 → Doctor A → Slot 1 → ONLINE → Token 2

### 🔹 Step 2: Walk-in Booking
POST medoc_health/setToken
Patient 3 → Doctor A → Slot 1 → WALK_IN → Token 3

### 🔹 Step 3: Paid Priority Booking
POST medoc_health/setToken
Patient 4 → Doctor A → Slot 1 → PAID

**Resulting order:**
1 → PAID
2 → ONLINE
3 → ONLINE
4 → WALK_IN

### 🔹 Step 4: Emergency Insertion
POST medoc_health/setToken
Patient 5 → Doctor A → Slot 1 → EMERGENCY

**Resulting order:**
1 → EMERGENCY
2 → PAID
3 → ONLINE
4 → ONLINE
5 → WALK_IN

### 🔹 Step 5: Cancellation
GET medoc_health/cancelToken
- Token cancelled
- Tokens after it shift up
- Slot capacity reduced

### 🔹 Step 6: No-Show
GET medoc_health/no_show
- Token marked NO_SHOW
- No shifting
- Capacity unchanged

  ### 🔹 Step 7: Duplicate Token Prevention
- Same patient attempts to book again for same doctor
- Request rejected with validation error

## 🚨 Edge Cases Handled
- Slot capacity overflow
- Emergency override
- Duplicate active token prevention
- Cancellation at any position
- No-show handling
- Slot-doctor mismatch
- Inactive doctor or slot
  
## ❌ Failure Handling
- Invalid inputs return meaningful errors
- Booking blocked for inactive doctors or slots
- Duplicate bookings prevented
- Capacity violations handled gracefully

## 🔌 API Endpoints (Key)

| Method | Endpoint | Description |
|------|---------|------------|
| POST | /medoc_health/addDoctor | Add doctor |
| POST | /medoc_health/addPatient | Add patient |
| POST | /medoc_health/addSlot | Create OPD slot |
| POST | /medoc_health/setToken| Generate token |
| GET | /cancelToken/{tokenNumber} | Cancel token |

Swagger UI is enabled for interactive testing.

## 🧠 Design Decisions & Trade-offs
- Token number is **slot-scoped**, not global
- Priority derived from source to avoid duplication
- No-show does not free capacity (realistic OPD behavior)
- Emergency tokens override ordering but preserve consistency
- Simulation done via Swagger for reproducibility

## 🚀 Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Swagger / OpenAPI

## ✅ Conclusion
This implementation demonstrates a **real-world OPD token allocation system** with dynamic prioritization, robust edge-case handling, and clean API design.  
The system is extensible, transaction-safe, and aligns closely with practical hospital OPD workflows.
