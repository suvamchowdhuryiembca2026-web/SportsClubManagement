const params        = new URLSearchParams(window.location.search);
const athleteId     = params.get("athleteId");
const eventId       = params.get("eventId");
const amount        = params.get("amount");
const eventName     = params.get("eventName");

if (!athleteId || !eventId || !amount) {
    alert("Invalid session. Please go back and try again.");
} else {
    document.getElementById("athleteId").value  = athleteId;
    document.getElementById("eventId").value    = eventId;
    document.getElementById("amount").value     = amount;
    document.getElementById("eventTitle").textContent  = eventName || "Event Registration";
    document.getElementById("displayAmount").textContent =
        "₹" + parseFloat(amount).toLocaleString("en-IN");
}

async function startPayment() {

    const aId = document.getElementById("athleteId").value;
    const eId = document.getElementById("eventId").value;
    const amt = document.getElementById("amount").value;

    if (!aId || !eId || !amt) {
        alert("Missing details. Please go back and try again.");
        return;
    }

    try {
        // STEP 1: CREATE ORDER - same as admission but hits EventPaymentServlet
        const response = await fetch("EventPaymentServlet", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: "athleteId=" + aId + "&eventId=" + eId + "&amount=" + amt
        });

        const order = await response.json();

        if (order.error) {
            alert("Error: " + order.error);
            return;
        }

        if (!order.id) {
            alert("Could not create payment order. Please try again.");
            return;
        }

        // STEP 2: OPEN RAZORPAY - same structure as admissionPayment.js
        const options = {
            key: "rzp_test_StBIfYA8nax4IQ",
            amount: order.amount,
            currency: order.currency,
            name: "Sports Club Management",
            description: order.event_name,
            order_id: order.id,
            handler: async function (razorpayResponse) {

                // STEP 3: VERIFY - same pattern as VerifyAdmissionPaymentServlet
                const verifyResponse = await fetch("EventPaymentVerifyServlet", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body:
                        "paymentId="             + order.db_payment_id +
                        "&razorpay_payment_id="  + razorpayResponse.razorpay_payment_id +
                        "&razorpay_order_id="    + razorpayResponse.razorpay_order_id +
                        "&razorpay_signature="   + razorpayResponse.razorpay_signature +
                        "&eventId="              + eId +
                        "&athleteId="            + aId
                });

                const result = await verifyResponse.text();

                if (result.trim() === "success") {
                    window.location.href = "paymentSuccess.html";
                } else {
                    window.location.href = "paymentFailed.html";
                }
            },
            prefill: { name: "", email: "", contact: "" },
            theme: { color: "#2563eb" },
            modal: {
                ondismiss: function () {
                    alert("Payment cancelled. You can register again from the Events tab.");
                }
            }
        };

        const razorpay = new Razorpay(options);
        razorpay.open();

    } catch (error) {
        console.error(error);
        alert("Payment initialization failed. Please try again.");
    }
}