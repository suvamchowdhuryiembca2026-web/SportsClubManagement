# 🏟️ Sports Ecosystem Management Platform
## Pure Java Servlet-Based Enterprise System — Zero Frameworks

> A full-stack, rule-driven sports management platform that handles athlete onboarding, document verification, payment-gated access, and event participation — built entirely from raw Java Servlets, JDBC, and Oracle SQL. No Spring Boot. No Hibernate. No shortcuts.

![Java](https://img.shields.io/badge/Java-Servlets%20Only-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-XE%20Database-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![Tomcat](https://img.shields.io/badge/Apache-Tomcat%209-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)
![Razorpay](https://img.shields.io/badge/Razorpay-Payment%20Gateway-02042B?style=for-the-badge&logo=razorpay&logoColor=3395FF)
![No Framework](https://img.shields.io/badge/Framework-NONE-lightgrey?style=for-the-badge)

---

## 📌 Table of Contents

1. [Project Vision](#-1-project-vision)
2. [The Real-World Problem This Solves](#-2-the-real-world-problem-this-solves)
3. [System Phases](#-3-system-phases)
4. [The Core Business Logic](#-4-the-core-business-logic--most-important-part)
5. [Athlete Lifecycle](#-5-athlete-lifecycle)
6. [Dashboard Intelligence](#-6-dashboard-intelligence)
7. [System Architecture](#-7-system-architecture)
8. [Module Breakdown](#-8-module-breakdown)
9. [Database Design](#-9-database-design)
10. [Payment Flow](#-10-payment-flow--end-to-end)
11. [Technology Stack](#-11-technology-stack)
12. [Security Model](#-12-security-model)
13. [Honest Limitations](#-13-honest-limitations)
14. [Future Roadmap](#-14-future-roadmap)
15. [What This Project Teaches](#-15-what-this-project-teaches)
16. [Author](#-16-author)

---

## 🎯 1. Project Vision

The Sports Ecosystem Platform is designed to simulate a **real sports organization** — the kind that exists in every city, every school, every state-level federation — but has never had proper software built for it.

It is not a demo. It is not a tutorial project. It is a **working, rule-driven digital ecosystem** where:

- Athletes apply for admission and go through a real verification process
- Coaches apply and get reviewed by an administrator
- An admin controls who gets in and who does not
- Payments unlock real features — not fake, not mocked
- Events are restricted based on verified status and payment history

It behaves like systems you have actually used:

| Real-World System | What It Does | This Platform Does the Same |
|---|---|---|
| University admission portal | Apply → Review → Pay → Access | ✅ Exact same flow |
| Sports federation membership | Register → Verify → Pay fees → Participate | ✅ Exact same flow |
| Gym membership system | Enroll → Approve → Pay → Use facilities | ✅ Exact same flow |

---

## 🌍 2. The Real-World Problem This Solves

Walk into any local sports club in India. Here is what you will find:

- **Registration** happens on a paper form or a WhatsApp message
- **Verification** means someone physically checks a photocopy of documents
- **Payments** are done by cash or UPI transfer with no proper tracking
- **Event registration** is a first-come-first-served WhatsApp broadcast
- **The admin** has no dashboard — just a notebook and memory

This creates real problems:

- Fake registrations with no verification
- Lost payment records
- No way to check who is eligible for what event
- Zero accountability in approvals
- Coaches with no documented credentials

**This platform solves every single one of these problems** by creating a gated, rule-driven digital system where nothing happens without the right checks passing first.

---

## 🚀 3. System Phases

### 🟢 Phase 1 — Core Management System

The foundation layer. Everything needed to run a sports club digitally.

| Feature | What It Does |
|---|---|
| Athlete registration | Multi-step form fills 5 database tables in one transaction |
| Coach registration | Application system with specialization and experience fields |
| Document upload | Photo, birth certificate, and ID proof stored with structured paths |
| Admin dashboard | Live stats, athlete table, coach table, approval controls |
| Email automation | Automatic emails on registration, approval, rejection |
| Event management | Admin creates events with banners, fees, and deadlines |

### 🔵 Phase 2 — Intelligent Access Control Layer

The smart layer. This is what separates this system from a basic CRUD app.

| Feature | What It Does |
|---|---|
| Payment-based unlocking | Features literally do not exist until payment is confirmed |
| Event visibility rules | Events only appear to athletes who qualify |
| Register button locking | The button is present or absent based on server-side logic |
| Status-driven dashboard | Every UI element responds to the athlete's current state |
| Razorpay integration | Live payment gateway with real order creation and verification |
| Business rule engine | Two boolean flags control access to the entire system |

---

## 🧠 4. The Core Business Logic — Most Important Part

This system is **not just CRUD**. It is a **rule-driven access control engine**.

Every time an athlete loads their dashboard, the server runs this check:

```java
// Is the athlete approved by admin?
boolean isApproved = "APPROVED".equalsIgnoreCase(status);

// Has the athlete paid the admission fee?
boolean admissionPaid = false;
for (Payment p : payments) {
    if ("ADMISSION".equalsIgnoreCase(p.getPaymentType())
            && "SUCCESS".equalsIgnoreCase(p.getPaymentStatus())) {
        admissionPaid = true;
        break;
    }
}

// Only BOTH together unlock full access
boolean canRegisterEvent = isApproved && admissionPaid;
```

Two boolean values. Four possible states. Each state produces a completely different dashboard experience.

### 🔐 Athlete Access Matrix

| Status | Admission Paid | Events Visible | Register Button |
|--------|---------------|----------------|-----------------|
| PENDING | ❌ No | ❌ Hidden | 🔒 Locked |
| REJECTED | ❌ No | ❌ Hidden | 🔒 Locked |
| APPROVED | ❌ No | ✅ Visible | 🔒 Locked |
| APPROVED | ✅ Yes | ✅ Visible | ✅ **Enabled** |

### What This Means in Plain English

- **Pending athlete** → sees their dashboard, sees their profile, sees nothing else. The system is waiting.
- **Rejected athlete** → told clearly. No events, no payment button, no false hope.
- **Approved but not paid** → can see all upcoming events in full detail. Can read about them. Cannot register. The button is replaced with a notice: *"Complete admission payment first."*
- **Approved and paid** → full access. Register button appears. Every event is open.

> This is a **state machine**. The athlete moves through states. Each state unlocks the next. You cannot skip a step.

---

## 🧱 5. Athlete Lifecycle

```
STEP 1 ──► Athlete submits registration form
              (fills ATHLETE + GUARDIAN + ADDRESS + SPORTS_PROFILE + DOCUMENTS)
                              │
STEP 2 ──► Status = PENDING
              Admin sees the application on dashboard
                              │
STEP 3 ──► Admin reviews documents
              │                    │
           APPROVED             REJECTED
              │                    │
              │              Athlete is notified
              │              by email. Journey ends.
              │
STEP 4 ──► Email sent: "Your registration has been approved"
              Payment tab is now unlocked on dashboard
                              │
STEP 5 ──► Athlete pays ₹5000 admission fee via Razorpay
              Payment row updated to SUCCESS in database
                              │
STEP 6 ──► Full system access unlocked
              Events tab shows all available events
                              │
STEP 7 ──► Athlete pays event registration fee via Razorpay
              Row inserted into EVENT_REGISTRATION table
                              │
STEP 8 ──► Athlete is officially registered for the event ✓
```

---

## 📊 6. Dashboard Intelligence

The athlete dashboard is not a static page. It is a **live, state-aware interface** that renders differently for every athlete based on their current status.

### What Changes Dynamically

| Dashboard Element | PENDING | APPROVED (unpaid) | APPROVED + PAID |
|---|---|---|---|
| Status badge | 🟡 Pending | 🟢 Approved | 🟢 Approved |
| Activity feed | Shows submission steps | Shows approval step | Shows payment success |
| Payment tab | Locked notice | Pay Now button | Already Paid badge |
| Events tab | Locked notice | Shows events | Shows events + Register |
| Register button | Does not exist | Does not exist | Appears and works |

### How It Is Built

All of this logic runs inside a single `AthleteDashboardServlet`. The servlet fetches the athlete's profile, payment history, and event list in one request. Then it renders the entire HTML page based on those three data points. The frontend has zero decision-making power — the server decides everything.

---

## 🏗️ 7. System Architecture

```
┌─────────────────────────────────────────┐
│           FRONTEND LAYER                │
│   HTML5  ·  CSS3  ·  JavaScript         │
│   Plain browser APIs. No frameworks.    │
└─────────────────┬───────────────────────┘
                  │  HTTP Request
                  ▼
┌─────────────────────────────────────────┐
│        SERVLET CONTROLLER LAYER         │
│   AthleteDashboardServlet               │
│   AdminDashboardServlet                 │
│   EventPaymentServlet                   │
│   EventPaymentVerifyServlet             │
│   CreateAdmissionOrderServlet           │
│   VerifyAdmissionPaymentServlet         │
│   ExportExcelServlet  · and more        │
└─────────────────┬───────────────────────┘
                  │  Method calls
                  ▼
┌─────────────────────────────────────────┐
│            DAO LAYER                    │
│   athleteDAO  ·  paymentDAO             │
│   EventDAO  ·  coachDAO                 │
│   Pure JDBC. Every SQL written by hand. │
└─────────────────┬───────────────────────┘
                  │  JDBC Connection
                  ▼
┌─────────────────────────────────────────┐
│         ORACLE XE DATABASE              │
│   Normalized schema                     │
│   Sequences for all primary keys        │
│   PreparedStatements only               │
└─────────────────────────────────────────┘
```

There is no magic. No annotation scanning. No dependency injection container. Every layer is explicit. You can trace any request from the browser hit to the database row and back — line by line.

---

## 🔁 8. Module Breakdown

### 👤 Athlete Module

Everything about the athlete's journey from stranger to registered member.

- Registration form submits to a servlet that inserts into 5 tables in a single JDBC transaction
- If any insert fails, the entire transaction rolls back — no partial data
- Status field on ATHLETE table drives the entire access control system
- Session-based authentication — every servlet checks `session.getAttribute("athleteId")` before doing anything

### 🏋️ Coach Module

A parallel system for coaching staff.

- Coaches apply with their specialization, years of experience, and coaching level
- Admin reviews and approves or rejects coach applications
- Approved coaches are visible in the admin dashboard coach table

### 🛡️ Admin Module

The control center. Only accessible to admin sessions.

- Live stats cards: total athletes, pending, approved, rejected
- Full athlete table with approve/reject buttons per row
- Full coach table with the same controls
- Event creation form: name, sport type, date, location, description, fee, banner image, last registration date
- Event registrations view: see exactly which athletes registered for which event
- Excel export of athlete data using Apache POI

### 💳 Payment Module

The engine behind the access unlock system.

- Admission payment: ₹5000 one-time fee that activates the account
- Event payment: variable fee per event
- Both follow the same three-step pattern: create order → open Razorpay → verify server-side
- Every payment is stored in the PAYMENT table with Razorpay order ID and payment ID
- Payment status starts as PENDING and is updated to SUCCESS after server-side verification

### 🏆 Events Module

Event creation, discovery, and registration.

- Admin creates events with full details and a banner image URL
- Athletes browse events on their dashboard (only if approved)
- Each event shows name, sport type, date, location, and registration fee
- Athletes with admission paid see a Register button — others see a locked notice
- On successful payment, a row is inserted into EVENT_REGISTRATION with status REGISTERED and payment status PAID

### 📧 Email Automation Module

The communication layer.

- Registration received confirmation sent immediately after form submission
- Approval email sent when admin clicks Approve
- Rejection email sent when admin clicks Reject
- Coach lifecycle emails at each stage
- Every email is logged to the EMAIL_LOG table with timestamp and recipient

---

## 🗄️ 9. Database Design

### Schema Overview

```
ATHLETE ──────────┬── GUARDIAN
    │              ├── ADDRESS
    │              ├── SPORTS_PROFILE
    │              └── DOCUMENTS
    │
    ├── PAYMENT (admission + events)
    │
    └── EVENT_REGISTRATION ──── EVENTS
```

### Table Reference

| Table | Primary Key | Purpose |
|---|---|---|
| ATHLETE | ATHLETE_SEQ.NEXTVAL | Core athlete record with STATUS field |
| COACH | COACH_SEQ.NEXTVAL | Coach applications and verification |
| GUARDIAN | GUARDIAN_SEQ.NEXTVAL | Parent/guardian details |
| ADDRESS | ADDRESS_SEQ.NEXTVAL | Athlete address details |
| SPORTS_PROFILE | SPORTS_SEQ.NEXTVAL | Sport type, category, club, achievements |
| DOCUMENTS | DOC_SEQ.NEXTVAL | File paths for uploaded documents |
| EVENTS | EVENT_SEQ.NEXTVAL | Events created by admin |
| EVENT_REGISTRATION | EVENT_REG_SEQ.NEXTVAL | Athlete-to-event registrations |
| PAYMENT | PAYMENT_SEQ.NEXTVAL | All payment records with Razorpay IDs |
| EMAIL_LOG | EMAIL_SEQ.NEXTVAL | Record of every automated email |

### Design Decisions

- All IDs use **Oracle Sequences** — explicit, controlled, no auto-increment surprises
- The **PAYMENT table handles both admission and events** using a `payment_type` column (`ADMISSION` or `EVENT_REGISTRATION`) — one table, full financial history
- **No ORM** — every query is a handwritten PreparedStatement. You know exactly what hits the database
- Transactions are managed manually with `con.setAutoCommit(false)`, `con.commit()`, and `con.rollback()` — multi-table inserts are atomic

---

## 💳 10. Payment Flow — End to End

Both admission and event payments follow the exact same three-step pattern. This is intentional — it makes the system consistent and easy to extend.

```
BROWSER                          SERVER                        RAZORPAY
   │                                │                              │
   │── POST (athleteId, amount) ──► │                              │
   │                                │── Create order API call ──► │
   │                                │◄── order_id, amount ────────│
   │                                │                              │
   │                    Save PENDING payment row to DB             │
   │                                │                              │
   │◄─── JSON (order_id, db_payment_id, amount) ─────────────────│
   │                                │                              │
   │── Open Razorpay checkout popup ──────────────────────────────►│
   │                                │                              │
   │◄── payment_id, signature ────────────────────────────────────│
   │                                │                              │
   │── POST (payment_id, signature, db_payment_id) ──────────────►│
   │                                │                              │
   │                    Update payment to SUCCESS                  │
   │                    Insert EVENT_REGISTRATION (events only)    │
   │                                │                              │
   │◄─── "success" ────────────────│                              │
   │                                │                              │
   │── Redirect to paymentSuccess.html                            │
```

### Key Technical Point

The `db_payment_id` is the critical link. It is created **before** the Razorpay popup opens and passed through the entire flow. This means even if the browser closes mid-payment, the server can still find the PENDING row and update it correctly when verification happens.

---

## 🧰 11. Technology Stack

| Layer | Technology | Version | Why This Choice |
|---|---|---|---|
| Backend | Java Servlets | javax.servlet | Core HTTP handling with no abstraction layer |
| Database | Oracle XE | 21c | Full relational DB with sequences and constraints |
| Connection | JDBC | Java 8+ | Raw SQL — complete control, no ORM overhead |
| Server | Apache Tomcat | 9.x | Industry-standard servlet container |
| Payments | Razorpay Java SDK | Latest | Live payment gateway — real orders, real money |
| Email | JavaMail API | 1.6 | SMTP-based automated emails |
| Excel | Apache POI | 5.x | Generate .xlsx reports from database data |
| Frontend | HTML5/CSS3/JS | — | Plain browser APIs — no React, no Vue, no build tools |

---

## 🔐 12. Security Model

### What Is Implemented

**SQL Injection Protection**
Every database query uses `PreparedStatement` with parameterized inputs. String concatenation in SQL does not exist in this codebase.

```java
// Always this:
PreparedStatement ps = con.prepareStatement("SELECT * FROM ATHLETE WHERE ATHLETE_ID = ?");
ps.setLong(1, athleteId);

// Never this:
Statement s = con.createStatement();
s.execute("SELECT * FROM ATHLETE WHERE ATHLETE_ID = " + athleteId); // ❌
```

**Session-Based Authentication**
Every single servlet starts with this check:

```java
HttpSession session = request.getSession(false);
if (session == null || session.getAttribute("athleteId") == null) {
    response.sendRedirect("UserLogin.html?status=sessionExpired");
    return;
}
```

No session means no access. Period. There is no URL you can type to bypass this.

**Server-Side Access Control**
The lock/unlock logic runs on the server, not in JavaScript. Disabling JS in the browser, editing the DOM, or faking a form submission changes nothing. The server re-checks status and payment on every request.

### What Is Planned

- BCrypt password hashing (currently stored plaintext — not production-ready)
- HTTPS enforcement
- JWT tokens for stateless authentication
- Role-based access control (RBAC) with multiple admin levels

---

## ⚠️ 13. Honest Limitations

This is a learning project. Here is what it does not have and why.

| Limitation | Reason | Impact |
|---|---|---|
| No password hashing | Intentional scope limit | Not safe for real deployment without BCrypt |
| No Spring Boot | Intentional — building from scratch first | More code, deeper understanding |
| No cloud deployment | Infrastructure not set up yet | Runs locally on Tomcat only |
| No microservices | Single WAR file | Fine for this scale, not for enterprise |
| No advanced security framework | Planned for next phase | Basic session auth only |
| POI JAR conflict | Apache POI version mismatch | Excel export fails until JAR versions are aligned |

> These are limitations by design, not by ignorance. Building without a framework first is the only way to truly understand what the framework is doing when you add it later.

---

## 🚀 14. Future Roadmap

### Short Term
- Fix Apache POI JAR version conflict for Excel export
- Add BCrypt password hashing
- Add signature verification to payment verify servlets

### Medium Term
- Spring Boot migration — same features, compare the code difference
- REST API layer so a mobile app can connect
- JWT authentication replacing session-based auth
- Cloud deployment on AWS or Railway

### Long Term
- Microservices split: athlete service, payment service, events service
- Redis caching for dashboard stats
- Advanced analytics dashboard with charts
- Mobile app (Android/iOS)
- Real-time notifications

---

## 🎓 15. What This Project Teaches

Building this without any framework forces you to understand things that most developers never think about because the framework hides them.

**Backend fundamentals**
- How HTTP requests travel from browser to server and back, step by step
- How sessions work at the cookie and server memory level
- How to write SQL that cannot be injected
- How JDBC transactions work — commit, rollback, autocommit

**System design**
- How to structure a multi-table insert as an atomic operation
- How to build an access control system from pure boolean logic
- How to separate database logic (DAO) from business logic (Servlet) cleanly
- How to design a payment flow that handles browser crashes and double-submissions

**Payment engineering**
- How Razorpay orders work: create server-side, open client-side, verify server-side
- Why you create the PENDING row before the popup opens
- How the db_payment_id links the browser flow to the server record

**Professional practices**
- PreparedStatement discipline — never a raw concatenated query
- Session validation on every protected endpoint
- Honest error handling that does not leak stack traces to users

> This is not just a project. It is proof that you understand how the internet actually works.

---

## 👨‍💻 16. Author

**Suvam Chowdhury**

Full Stack Java Developer

Specialized in backend architecture, database systems, and servlet-based enterprise applications. Builds systems from scratch to understand them from the ground up — before adding the abstractions that make them faster to build but harder to understand.

---

<div align="center">

**Built with Java Servlets · Oracle XE · Apache Tomcat · Razorpay**

*Zero frameworks. Every line written by hand. Every concept understood from first principles.*

</div>

---

## 📊 Excel Export System

The admin dashboard has an **Export button** that generates a fully formatted, color-coded `.xlsx` file containing the complete data of every registered athlete — instantly downloadable with one click.

### What the exported file contains

Every athlete's record is pulled from the database and written into a structured Excel sheet with:

- Athlete ID, full name, age, gender, mobile, email
- Sport type, category, club name
- Registration status (PENDING / APPROVED / REJECTED)
- Guardian name and contact
- Address and state
- Admission payment status

### Formatting applied to the file

This is not a plain data dump. The file is styled using **Apache POI** with:

- **Header row** — bold text, colored background, distinct font to separate it from data
- **Color-coded status rows** — approved athletes, pending athletes, and rejected athletes each get a different row background color so the admin can scan the sheet at a glance
- **Column widths auto-sized** — no truncated text, every column fits its content
- **Proper cell types** — numbers as numbers, text as text, not everything as a string

### How it works technically

```
Admin clicks Export button
        ↓
GET request hits ExportExcelServlet
        ↓
Servlet calls DAO to fetch all athletes from DB
        ↓
Apache POI creates XSSFWorkbook in memory
        ↓
Rows written with conditional formatting per status
        ↓
Response headers set:
  Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
  Content-Disposition: attachment; filename="athletes.xlsx"
        ↓
File streams directly to browser — download starts immediately
```

No file is saved on the server. The Excel file is generated in memory and streamed directly to the browser as a download. Every time the button is clicked, the data is freshly pulled from the database.

### Common issue and fix

If you see `java.lang.NoSuchFieldError: Factory` when clicking Export, it means you have **mismatched Apache POI JAR versions** in `WEB-INF/lib`. The fix is to ensure all POI JARs are from the same version:

```
poi-5.x.x.jar
poi-ooxml-5.x.x.jar
poi-ooxml-full-5.x.x.jar
xmlbeans-5.x.x.jar
```

Remove any older versions mixed in from a previous dependency.

---

## 📧 Email Automation System

Every status change in the system triggers an automatic email. This is not manual — the admin never types an email. The moment a button is clicked, the email goes out.

### How it is structured

A dedicated `EmailService` class lives in the `util` package. It has one **core email method** where the SMTP configuration is set up:

```java
// Core setup inside EmailService
Properties props = new Properties();
props.put("mail.smtp.host", "smtp.gmail.com");
props.put("mail.smtp.port", "587");
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "true");
```

On top of this core method, three template methods are built — one for each lifecycle event. The servlets call these methods directly after the business logic runs.

### The three email templates

**1. Registration submitted — Pending email**

Triggered the moment an athlete or coach successfully completes registration.

- Sent automatically after the registration servlet confirms the DB insert succeeded
- Tells the applicant their registration was received and is under review
- Sets expectation: they will hear back once admin reviews their documents
- Status in DB is set to `PENDING` at the same time this email fires

**2. Approved email**

Triggered when the admin clicks the **Approve** button on an individual athlete or coach profile.

- Sent immediately from the approval servlet after the status is updated to `APPROVED` in the DB
- Congratulates the athlete/coach on approval
- For athletes: tells them they can now log in and complete admission payment to activate their account
- Professional, positive tone

**3. Rejected email**

Triggered when the admin clicks the **Reject** button on an individual athlete or coach profile.

- Sent immediately from the rejection servlet after the status is updated to `REJECTED` in the DB
- Informs the applicant their application was not approved
- Clear and respectful — no harsh language

### The exact flow

```
Athlete submits registration form
        ↓
RegistrationServlet inserts into DB → status = PENDING
        ↓
EmailService.sendPendingEmail(athlete.getEmail(), athlete.getFullName())
        ↓
Athlete receives: "Registration received — under review"

──────────────────────────────────────────

Admin opens athlete profile → clicks Approve
        ↓
ApprovalServlet updates ATHLETE.STATUS = 'APPROVED'
        ↓
EmailService.sendApprovalEmail(athlete.getEmail(), athlete.getFullName())
        ↓
Athlete receives: "Your registration has been approved"

──────────────────────────────────────────

Admin opens athlete profile → clicks Reject
        ↓
RejectionServlet updates ATHLETE.STATUS = 'REJECTED'
        ↓
EmailService.sendRejectionEmail(athlete.getEmail(), athlete.getFullName())
        ↓
Athlete receives: "Your application was not approved"
```

### Individual profile view

The admin dashboard shows every athlete and coach in a table. Each row has a **View button**. Clicking it opens the full profile of that athlete or coach showing:

- Every personal detail, sport profile, guardian info, and address
- Uploaded document references
- Current status badge
- **Approve button** and **Reject button** — clicking either triggers the status update AND the corresponding email in one action

The admin never has to go to a separate email client. The communication is fully automatic and tied directly to the approval workflow.

### Why this matters

In a real sports club, applicants are anxious after submitting. They do not know if their form was received, if their documents are being reviewed, or if they were approved or rejected. This email system eliminates that uncertainty completely — every state change is communicated instantly and automatically.


---

## 📄 Athlete Profile View — Admin Evaluation Center

When the admin clicks the **View** button next to any athlete in the dashboard table, they are taken to a dedicated **Athlete Profile View** page — a full evaluation screen that gives the admin everything they need to make an approval decision.

This is not just a profile display page. It is the **command center for the entire athlete verification workflow**.

### What the admin sees on this page

- Complete personal details — name, age, gender, mobile, email
- Sport profile — sport type, category, club name, achievements
- Guardian and address information
- The athlete's **uploaded profile photo** rendered directly on the page
- Clickable links or inline viewers for all uploaded documents
- Current status badge
- **Approve button** — triggers status update + approval email
- **Reject button** — triggers status update + rejection email
- **Export as PDF toggle button** — generates a formatted athlete resume

---

## 🖼️ Document Verification System

Before clicking Approve or Reject, the admin needs to verify that the athlete is real — not someone who submitted fake details or someone else's documents.

The profile view page displays all uploaded documents so the admin can manually inspect them:

### Documents visible to admin

| Document | Purpose |
|---|---|
| Profile photo | Confirm the person matches their stated identity |
| Birth certificate | Verify age and date of birth against what was entered |
| ID proof | Cross-check name, address, and identity |

The athlete uploads these during registration. They are stored on the server with structured file paths and referenced in the DOCUMENTS table. When the admin opens the profile view, the files are served directly — the admin can see them without downloading or leaving the page.

### Why this matters

This is what makes the system a **real verification workflow**, not just an approval button. The admin can:

- Look at the profile photo and confirm it matches a real person
- Open the birth certificate and check if the age the athlete entered matches
- Check the ID proof for name consistency

Only after this manual check does the admin click Approve or Reject. The email fires automatically the moment that button is clicked.

---

## 📑 Export as PDF — Athlete Resume Generator

Every athlete profile page has an **Export as PDF** toggle button. Clicking it generates a professionally formatted **athlete resume PDF** on the spot — a document that represents the athlete's full profile in a portable, shareable format.

### What the PDF contains

- **Athlete's uploaded profile photo** — the actual image the athlete submitted during registration, rendered at the top of the resume
- Full name, age, gender, contact details
- Sport type, category, club name, achievements
- Guardian information
- Address and state
- Current registration status
- Athlete ID

### Why the profile photo matters here

The photo is not a placeholder or an avatar. It is the **real image uploaded by the athlete** during registration — the same one the admin sees when verifying the profile. This means the PDF resume is an accurate, personalized document tied to that specific athlete's identity.

### How it works technically

```
Admin clicks Export as PDF on athlete profile page
        ↓
Request hits AthleteViewServlet with the athlete's ID
        ↓
Servlet fetches complete athlete profile from DB
        (athlete + guardian + address + sports profile + documents)
        ↓
Profile photo path is fetched from DOCUMENTS table
        ↓
PDF is generated in memory using a PDF library
        (iText / Flying Saucer / Apache PDFBox)
        ↓
Photo is embedded into the PDF at the top of the resume
        ↓
Response headers set:
  Content-Type: application/pdf
  Content-Disposition: attachment; filename="athlete_[ID]_resume.pdf"
        ↓
PDF streams directly to browser as a download
```

No file is saved on the server. The PDF is built in memory from live database data and the stored photo, then streamed directly to the browser.

### Where the photo appears across the system

The athlete's uploaded profile photo is not isolated to one place. It flows through the entire system:

| Location | How the photo is used |
|---|---|
| Athlete dashboard sidebar | Displayed as the athlete's avatar in the left panel |
| Admin dashboard athlete table | Shown as a thumbnail next to the athlete's name |
| Admin profile view page | Full-size display for identity verification |
| Exported PDF resume | Embedded at the top of the generated document |

This consistency means the photo uploaded once during registration becomes the athlete's identity across the entire platform — on their own dashboard, in the admin's view, and in the exported resume.