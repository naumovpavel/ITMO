function updateClock() {
    const now = new Date();

    const timeElement = document.getElementById('time');
    const dateElement = document.getElementById('date');

    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    const day = String(now.getDate()).padStart(2, '0');
    const month = String(now.getMonth() + 1).padStart(2, '0'); // Months are 0-indexed
    const year = now.getFullYear();

    const timeString = `${hours}:${minutes}:${seconds}`;
    const dateString = `${day}.${month}.${year}`;

    timeElement.textContent = timeString;
    dateElement.textContent = dateString;
}

// Update the clock immediately and then every 10 seconds
updateClock();
setInterval(updateClock, 10000);
