<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

    <title>Athlete Resume</title>

    <meta charset="UTF-8">

    <style>

       * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Segoe UI', sans-serif;
    background: #eef2f7;
    padding: 20px;
    color: #111827;
}

/* ===== A4 CONTAINER ===== */
.resume {
    width: 210mm;
    min-height: 297mm;
    margin: auto;
    background: #ffffff;
    display: flex;
    box-shadow: 0 8px 25px rgba(0,0,0,0.10);
}

/* ===== LEFT PANEL ===== */
.left {
    width: 32%;
    background: #1f2937; /* neutral dark (not blue-heavy) */
    color: #ffffff;
    padding: 28px 20px;
}

/* PROFILE */
.profile {
    text-align: center;
    margin-bottom: 28px;
}

.profile img {
    width: 130px;
    height: 130px;
    border-radius: 50%;
    object-fit: cover;
    border: 3px solid #ffffff;
}

.profile h1 {
    font-size: 22px;
    margin-top: 12px;
    font-weight: 600;
}

.profile p {
    font-size: 13px;
    color: #d1d5db;
    margin-top: 4px;
}

/* LEFT SECTION */
.section {
    margin-top: 25px;
}

.section h2 {
    font-size: 13px;
    text-transform: uppercase;
    letter-spacing: 1px;
    color: #e5e7eb;
    margin-bottom: 10px;
    border-bottom: 1px solid #374151;
    padding-bottom: 6px;
}

.info {
    margin-bottom: 12px;
    font-size: 14px;
    color: #f3f4f6;
}

.info span {
    display: block;
    font-size: 12px;
    color: #9ca3af;
}

/* ===== RIGHT PANEL ===== */
.right {
    width: 68%;
    padding: 35px;
    background: #ffffff;
}

/* SECTION HEADINGS */
.right-section {
    margin-bottom: 28px;
}

.right-section h2 {
    font-size: 18px;
    color: #111827;
    border-left: 4px solid #6b7280; /* neutral gray */
    padding-left: 10px;
    margin-bottom: 14px;
    font-weight: 600;
}

/* CARD */
.card {
    background: #ffffff;
    border: 1px solid #e5e7eb;
    border-left: 4px solid #9ca3af; /* neutral accent */
    padding: 16px;
    border-radius: 10px;
}

.card h3 {
    font-size: 16px;
    margin-bottom: 8px;
    color: #111827;
}

.card p {
    font-size: 14px;
    color: #374151;
    line-height: 1.6;
}

/* ===== BADGES (COLOR OPTIONAL BUT PRINT SAFE) ===== */
.badge {
    display: inline-block;
    border: 1px solid #6b7280;
    color: #111827;
    padding: 6px 12px;
    border-radius: 999px;
    font-size: 12px;
    margin: 5px 5px 0 0;
    background: transparent;
}

/* ===== PRINT MODE (FORCE BLACK & WHITE CLEAN OUTPUT) ===== */
@media print {

    body {
        background: #ffffff !important;
        color: #000000 !important;
        -webkit-print-color-adjust: exact;
        print-color-adjust: exact;
    }

    .resume {
        box-shadow: none !important;
    }

    .left {
        background: #111827 !important; /* solid black/gray */
        color: #ffffff !important;
        -webkit-print-color-adjust: exact;
        print-color-adjust: exact;
    }

    .right {
        background: #ffffff !important;
    }

    .card {
        border: 1px solid #000000 !important;
        border-left: 3px solid #000000 !important;
    }

    .badge {
        border: 1px solid #000000 !important;
        color: #000000 !important;
    }

    .right-section h2 {
        border-left: 3px solid #000000 !important;
    }
}
    </style>

</head>

<body>

<div class="resume">

    <!-- LEFT -->

    <div class="left">

        <div class="profile">

            <img src="${docs.photoPath}">

            <h1>${athlete.fullName}</h1>

            <p>${sports.sportType} Athlete</p>

        </div>

        

        <div class="section">

            <h2>Contact</h2>

            <div class="info">
                <span>Email</span>
                ${athlete.email}
            </div>

            <div class="info">
                <span>Phone</span>
                ${athlete.mobile}
            </div>

            <div class="info">
                <span>City</span>
                ${address.city}
            </div>

        </div>

        <div class="section">

            <h2>Guardian</h2>

            <div class="info">
                <span>Name</span>
                ${guardian.guardianName}
            </div>

            <div class="info">
                <span>Relation</span>
                ${guardian.relation}
            </div>

            <div class="info">
                <span>Emergency</span>
                ${guardian.emergencyContact}
            </div>

        </div>

        <!-- STATUS -->

        <div class="section">

            <h2>Status</h2>

            <div class="info">
                <span>Current Status</span>
                ${athlete.status}
            </div>

        </div>

    </div>

    <!-- RIGHT -->

    <div class="right">

        <!-- SUMMARY -->

        <div class="right-section">

            <h2>Profile Summary</h2>

            <div class="card">

                <p>

                    Hi I am a dedicated athlete specializing in
                    ${sports.sportType}
                    with strong discipline, teamwork,
                    and competitive spirit.

                    Passionate about representing
                    ${sports.clubName}
                    in district, state, and national level events.

                </p>

            </div>

        </div>

        <!-- SPORTS -->

        <div class="right-section">

            <h2>Sports Profile</h2>

            <div class="card">

                <h3>${sports.clubName}</h3>

                <p>

                    Category :
                    ${sports.category}

                    <br><br>

                    Training Level :
                    ${sports.trainingLevel}

                </p>

            </div>

        </div>

        <!-- ADDRESS -->

        <div class="right-section">

            <h2>Address</h2>

            <div class="card">

                <p>

                    ${address.fullAddress}

                    <br><br>

                    ${address.city},
                    ${address.district},
                    ${address.state}

                    -
                    ${address.pinCode}

                </p>

            </div>

        </div>

        <!-- ACHIEVEMENTS -->

        <div class="right-section">

            <h2>Highlights</h2>

            <div class="badge">
                Registered Athlete
            </div>

            <div class="badge">
                ${sports.trainingLevel}
            </div>

            <div class="badge">
                ${sports.sportType}
            </div>

            <div class="badge">
                ${sports.clubName}
            </div>

        </div>

    </div>

</div>
<script>

window.onload = function () {
    window.focus();
    window.print();
};

window.onafterprint = function () {
    window.close();
};

</script>
</body>

</html>