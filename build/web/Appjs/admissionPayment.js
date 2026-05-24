async function startPayment()
{
    try
    {
        const athleteId =
        document.getElementById("athleteId").value;

        const response =
        await fetch(
            "CreateAdmissionOrderServlet",
            {
                method:"POST",

                headers:
                {
                    "Content-Type":
                    "application/x-www-form-urlencoded"
                },

                body:
                "athleteId=" + athleteId
            }
        );

        const order =
        await response.json();

        const options =
        {
            key: "YOUR_RAZORPAY_KEY",

            amount: order.amount,

            currency: order.currency,

            name: "Sports Club Management",

            description: "Admission Fee Payment",

            order_id: order.id,

            handler: async function (response)
            {
                const verifyResponse =
                await fetch(
                    "VerifyAdmissionPaymentServlet",
                    {
                        method:"POST",

                        headers:
                        {
                            "Content-Type":
                            "application/x-www-form-urlencoded"
                        },

                        body:
                        "paymentId=" +
                        order.receipt.replace("receipt_","") +

                        "&razorpay_order_id=" +
                        response.razorpay_order_id +

                        "&razorpay_payment_id=" +
                        response.razorpay_payment_id
                    }
                );

                const result =
                await verifyResponse.text();

                if(result === "success")
                {
                    window.location.href =
                    "paymentSuccess.html";
                }
                else
                {
                    window.location.href =
                    "paymentFailed.html";
                }
            },

            theme:
            {
                color:"#2563eb"
            }
        };

        const razorpay =
        new Razorpay(options);

        razorpay.open();
    }

    catch(error)
    {
        console.log(error);

        alert("Payment initialization failed");
    }
}
const params =
new URLSearchParams(window.location.search);

const athleteId =
params.get("athleteId");