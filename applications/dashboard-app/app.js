document.addEventListener("DOMContentLoaded", function () {

    healthCheck();
    footer()

});

function footer() {
    const date = new Date();
    document.getElementById("footer-year").textContent = date.getFullYear()
}

async function healthCheck() {

    while (true) {

        checkHealthService()

        await sleep(10000);

    }

}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function checkHealthService() {

    let options = {
        method: 'GET',
        mode: 'no-cors'
    }

    let response = await fetch("http://localhost/healthcheck", options);

    if (response.ok) {

        const services = await response.json();

        services.results.forEach((service) => {
            updateStatusDashboard(service)
        })

    }

}

function updateStatusDashboard(service) {
    let element = document.getElementById(service.name);
    if (service.ok) {
        element.title = "Service is Health"
        element.className = "left-space bi bi-check-circle health"
    } else {
        element.title = "Service is Unhealth"
        element.className = "left-space bi bi-ban unhealth"
    }
}