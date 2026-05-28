// READ athleteId FROM URL AND SET INTO HIDDEN INPUT
const params = new URLSearchParams(window.location.search);
const athleteIdFromUrl = params.get("athleteId");

if (!athleteIdFromUrl) {
    alert("Invalid session. Please register again.");
} else {
    document.getElementById("athleteId").value = athleteIdFromUrl;
}

async function startPayment() {
    const athleteId = document.getElementById("athleteId").value;

    if (!athleteId) {
        alert("Athlete ID missing. Please register again.");
        return;
    }

    try {
        // STEP 1: CREATE ORDER ON SERVER
        // Server inserts a PENDING payment row and returns Razorpay order + db_payment_id
        const response = await fetch("CreateAdmissionOrderServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "athleteId=" + athleteId
        });

        const order = await response.json();

        // order contains: id, amount, currency, db_payment_id (our DB row id)
        if (!order.id) {
            alert("Could not create payment order. Please try again.");
            return;
        }

        // STEP 2: OPEN RAZORPAY CHECKOUT
        const options = {
            key: "rzp_test_StBIfYA8nax4IQ",
            amount: order.amount,
            currency: order.currency,
            name: "Sports Club Management",
            description: "Admission Fee Payment",
            order_id: order.id,

            handler: async function (razorpayResponse) {
                // STEP 3: VERIFY PAYMENT ON SERVER
                // Send db_payment_id so server knows which DB row to update
                const verifyResponse = await fetch("VerifyAdmissionPaymentServlet", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body:
                        "paymentId=" + order.db_payment_id +
                        "&razorpay_order_id=" + razorpayResponse.razorpay_order_id +
                        "&razorpay_payment_id=" + razorpayResponse.razorpay_payment_id +
                        "&razorpay_signature=" + razorpayResponse.razorpay_signature +
                        "&athleteId=" + athleteId
                });

                const result = await verifyResponse.text();

                if (result.trim() === "success") {
                    window.location.href = "paymentSuccess.html";
                } else {
                    window.location.href = "paymentFailed.html";
                }
            },

            prefill: {
                name: "",
                email: "",
                contact: ""
            },

            theme: {
                color: "#2563eb"
            },

            modal: {
                ondismiss: function () {alert("Payment cancelled. Your registration is saved. Complete payment to activate your account.");}
            }
        };

        const razorpay = new Razorpay(options);
        razorpay.open();

    } catch (error) {
        console.error(error);
        alert("Payment initialization failed. Please try again.");
    }
}