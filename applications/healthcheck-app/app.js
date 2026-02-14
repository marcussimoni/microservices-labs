const express = require("express");
const app = express();
const fs = require('fs').promises;
const path = require('path');
const axios = require('axios');

const port = 3030

async function loadConfig() {
    const filePath = path.join('config', 'applications.json');
    const rawData = await fs.readFile(filePath, 'utf-8');
    return JSON.parse(rawData);
}

async function requestUrls() {
    const config = await loadConfig();

    const results = [];

    for (const target of config.targets) {
        try {
            console.log("url", target.url)
            const response = await axios.get(target.url);

            results.push({
                name: target.name,
                status: response.status,
                ok: true
            });
        } catch (error) {
            results.push({
                name: target.name,
                error: error.message,
                ok: false
            });
        }
    }

    const allServicesHealth = results.filter(result => !result.ok).length === 0

    return {
        allServicesHealth, results
    };
}

app.get("/health", async (req, res) => {

    const results = await requestUrls()
    res.json(results)

});

app.listen(port, () => console.log(`App Listening on port ${port}`));