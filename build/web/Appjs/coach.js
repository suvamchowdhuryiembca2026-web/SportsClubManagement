

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
        window.scrollTo({ top: 0, behavior: "smooth" });
    }
}

function prevStep() {
    if (currentStep > 0) {
        currentStep--;
        showStep(currentStep);
        window.scrollTo({ top: 0, behavior: "smooth" });
    }
}

function validateStep() {
    const inputs = steps[currentStep].querySelectorAll("input, select, textarea");
    let valid = true;

    inputs.forEach(i => {
        if (i.hasAttribute("required") && !i.value.trim()) {
            i.style.border = "2px solid red";
            valid = false;
        } else {
            i.style.border = "1px solid #ccc";
        }
    });

    return valid;
}

// ===================== PHOTO COMPRESSION =====================
document.addEventListener("DOMContentLoaded", function () {
    const photoInput = document.getElementById("photo");

    if (photoInput) {
        photoInput.addEventListener("change", function (e) {
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

                    const MAX_WIDTH  = 800;
                    const MAX_HEIGHT = 800;

                    let width  = img.width;
                    let height = img.height;

                    if (width > height) {
                        if (width > MAX_WIDTH) {
                            height = Math.round((height * MAX_WIDTH) / width);
                            width  = MAX_WIDTH;
                        }
                    } else {
                        if (height > MAX_HEIGHT) {
                            width  = Math.round((width * MAX_HEIGHT) / height);
                            height = MAX_HEIGHT;
                        }
                    }

                    canvas.width  = width;
                    canvas.height = height;
                    ctx.drawImage(img, 0, 0, width, height);

                    compressToLimit(canvas, file.name, photoInput, 0.8);
                };
            };
        });
    }
});

function compressToLimit(canvas, fileName, inputElement, quality) {
    canvas.toBlob(blob => {
        if (!blob) { alert("Compression failed."); return; }

        if (blob.size > 1024 * 1024 && quality > 0.2) {
            compressToLimit(canvas, fileName, inputElement, parseFloat((quality - 0.1).toFixed(1)));
            return;
        }

        if (blob.size > 1024 * 1024) {
            alert("Image is too large even after max compression. Please choose a smaller image.");
            inputElement.value = "";
            return;
        }

        const compressed = new File([blob], fileName, {
            type: "image/jpeg",
            lastModified: Date.now()
        });

        const dt = new DataTransfer();
        dt.items.add(compressed);
        inputElement.files = dt.files;

        console.log(`Compressed to: ${(compressed.size / 1024).toFixed(2)} KB at quality ${quality}`);
    }, "image/jpeg", quality);
}

// ===================== MODALS =====================
function showSuccessModal() {
    document.getElementById("successModal").style.display = "flex";

    setTimeout(() => {
        window.location.href = "index.html";
    }, 2500);
}

function showErrorModal() {
    document.getElementById("errorModal").style.display = "flex";
}

function closeModal() {
    document.getElementById("errorModal").style.display = "none";
}

// ===================== ON LOAD =====================
window.onload = function () {
    showStep(currentStep);

    const params = new URLSearchParams(window.location.search);
    const status = params.get("status");

    if (status === "success") {
        showSuccessModal();
    }

    if (status === "fail") {
        showErrorModal();
    }
};