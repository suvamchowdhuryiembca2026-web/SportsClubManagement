let currentStep = 0;

const steps = document.querySelectorAll(".form-step");
const stepper = document.querySelectorAll(".step");

function updateStepper() {

    stepper.forEach((s, i) => {
        s.classList.remove("active", "completed");

        if (i < currentStep) {
            s.classList.add("completed");
        } else if (i === currentStep) {
            s.classList.add("active");
        }
    });
}

function showStep(index) {
    steps.forEach((s, i) => {
        s.classList.remove("active");
        if (i === index) s.classList.add("active");
    });

    updateStepper();
}

function nextStep() {
    if (!validateStep()) return;

    if (currentStep < steps.length - 1) {
        currentStep++;
        showStep(currentStep);
    }
}

function prevStep() {
    if (currentStep > 0) {
        currentStep--;
        showStep(currentStep);
    }
}

function validateStep() {
    const inputs = steps[currentStep].querySelectorAll("input, select");
    let valid = true;

    inputs.forEach(i => {
        if (i.hasAttribute("required") && !i.value) {
            i.style.border = "2px solid red";
            valid = false;
        } else {
            i.style.border = "1px solid #ccc";
        }
    });

    return valid;
}

/* IMAGE COMPRESSION */
document.getElementById("photo").addEventListener("change", function (e) {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.readAsDataURL(file);

    reader.onload = function () {
        const img = new Image();
        img.src = reader.result;

        img.onload = function () {

            const canvas = document.createElement("canvas");
            const ctx = canvas.getContext("2d");

            const maxWidth = 800;
            const scale = maxWidth / img.width;

            canvas.width = maxWidth;
            canvas.height = img.height * scale;

            ctx.drawImage(img, 0, 0, canvas.width, canvas.height);

            canvas.toBlob(blob => {

                const compressed = new File([blob], file.name, {
                    type: "image/jpeg",
                    lastModified: Date.now()
                });

                const dt = new DataTransfer();
                dt.items.add(compressed);
                e.target.files = dt.files;

            }, "image/jpeg", 0.7);
        };
    };
});

showStep(currentStep);
function showSuccessModal() {
    document.getElementById("successModal").style.display = "flex";

    setTimeout(() => {
        window.location.href = "index.html";
    }, 1500);
}

function showErrorModal() {
    document.getElementById("errorModal").style.display = "flex";
}

function closeModal() {
    document.getElementById("errorModal").style.display = "none";
}
window.onload = function () {
    const params = new URLSearchParams(window.location.search);
    const status = params.get("status");

    if (status === "success") {
        showSuccessModal();

        setTimeout(() => {
            window.location.href = "index.html";
        }, 1500);
    }

    if (status === "fail") {
        showErrorModal();
    }
};