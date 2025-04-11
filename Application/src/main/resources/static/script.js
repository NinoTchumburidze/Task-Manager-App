document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.task-status').forEach(item => {

        item.addEventListener('click', function () {
            // Get the taskId from the image's data attribute
            const taskId = this.getAttribute('data-task-id');  // Task ID from the image
            console.log(taskId);
            // Get current status and check if task is done (using boolean comparison)
            const isDone = this.getAttribute('data-status') === 'true';  // If it's 'true', task is done

            // Toggle the status and change the image source based on the new state
            const newStatus = !isDone;  // Toggle the status
            this.setAttribute('data-status', newStatus);  // Update the data-status attribute

            // Change the image source based on the new status
            this.src = newStatus ? 'marked.png' : 'notMarked.png';  // Adjust the paths

            // Send an AJAX request to update the task status on the backend
            fetch('/tasks/updateTaskStatus', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'taskId=' + encodeURIComponent(taskId) + '&taskState=' + encodeURIComponent(newStatus)
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Task status updated:', data);
                })
                .catch(error => {
                    console.error('Error updating task status:', error);
                });

        });
    });
});
function loadDate(){
    var currentDate = new Date();
    var currentHour = new Date();

    // Get the current weekday
    var weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    var currentWeekday = weekdays[currentDate.getDay()];

    // Get the current time (hour)
    var currentHourVal = currentDate.getHours();
    var currentMinute = currentDate.getMinutes();
    var currentSecond = currentDate.getSeconds();

    // Format time with leading zeros if needed
    currentMinute = currentMinute < 10 ? '0' + currentMinute : currentMinute;
    currentSecond = currentSecond < 10 ? '0' + currentSecond : currentSecond;

    // Get the current date (month, day, year)
    var currentDay = currentDate.getDate();
    var currentMonth = currentDate.getMonth() + 1; // Months are zero-indexed
    var currentYear = currentDate.getFullYear();

    // Format the date and time as a string
    var dateString = currentWeekday + ", " + currentMonth + "/" + currentDay + "/" + currentYear;
    var hourString = currentHourVal + ":" + currentMinute + ":" + currentSecond;

    // Check if elements exist before setting textContent
    var currentDateElement = document.getElementById("currentDate");
    var currentHourElement = document.getElementById("currentHour");

    if (currentDateElement) {
        currentDateElement.textContent = dateString;
    } else {
        console.error("Element with ID 'currentDate' not found");
    }

    if (currentHourElement) {
        currentHourElement.textContent = hourString;
    } else {
        console.error("Element with ID 'currentHour' not found");
    }
}
function discardTask() {
    // Get the date input element
    var dateInput = document.getElementById('taskDate');

    // Remove the required attribute from the date input field
    dateInput.removeAttribute('required');

    // Optionally, redirect the user to the home page or perform other actions
    window.location.href = 'HomePage';
}

document.addEventListener('DOMContentLoaded', function() {

    loadDate();


});

