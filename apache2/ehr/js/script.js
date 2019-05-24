$(document).ready(function () {
    // get all the patients table on page load
    $.ajax({
        type: "POST",
        url: 'php/loadPatients.php',
        success: function (response) {
            var jsonData = JSON.parse(response);
            var table = $('#records_body');
            for (var i = 0; i < jsonData.patients.length; i++) {
                table.append("<tr>");
                table.append("<td>" + jsonData.patients[i].id + "</td>");
                table.append("<td>" + jsonData.patients[i].firstName + "</td>");
                table.append("<td>" + jsonData.patients[i].lastName + "</td>");
                table.append("<td>" + jsonData.patients[i].dateofbirth + "</td>");
                var allergiesList = jsonData.patients[i].allergies
                var allergies = "";
                for (var j = 0; j < allergiesList.length; j++) {
                    allergies += "<i class='fas fa-allergies'></i> " + allergiesList[j] + "<br/>"
                }
                table.append("<td>" + allergies + "</td>");
                var prescriptionsList = jsonData.patients[i].prescriptions;
                var prescriptions = "";
                for (var j = 0; j < prescriptionsList.length; j++) {
                    var drug = prescriptionsList[j];
                    prescriptions += "<i class='fas fa-pills'></i> " + drug.drugName + " [" + drug.dosage + "]" + "<br/>";
                }
                table.append("<td>" + prescriptions + "</td>");
                table.append("</tr>");
            }
        }
    });
    $('#createPatientButton').click(function () {
        var postData = $("#createPatientForm").serializeArray();
        var request = $.ajax({
            type: "POST",
            url: 'php/createPatient.php',
            data: postData,
            success: function (response) {
                if (response.includes("already exists")) {
                    toastr.error(response, { closeButton: true, timeOut: 4000, progressBar: true, allowHtml: true });
                }
                else {
                    toastr.success("PATIENT CREATED", { closeButton: true, timeOut: 3000, progressBar: true });
                    setTimeout(function () {
                        location.reload();
                    }, 3000);
                }
            },
            error: function (response) {
                toastr.error('An error has occured. Try again later', { closeButton: true, timeOut: 3000, progressBar: true, allowHtml: true });
            }
        });
    });
    $('#createAllergyButton').click(function () {
        var postData = $("#createAllergyForm").serializeArray();
        var request = $.ajax({
            type: "POST",
            url: 'php/createAllergy.php',
            data: postData,
            success: function (response) {
                toastr.success("ALLERGY CREATED", { closeButton: true, timeOut: 3000, progressBar: true });
                setTimeout(function () {
                    location.reload();
                }, 3000);
            },
            error: function (response) {
                toastr.error('An error has occured. Try again later', { closeButton: true, timeOut: 3000, progressBar: true, allowHtml: true });
            }
        });
    });
    $('#createPrescriptionButton').click(function () {
        var postData = $("#createPrescriptionForm").serializeArray();
        var request = $.ajax({
            type: "POST",
            url: 'php/createPrescription.php',
            data: postData,
            success: function (response) {
                if (response.includes('transaction returned with failure')){
                    toastr.error(response, { closeButton: true, timeOut: 10000, progressBar: true });
                }
                else{
                    toastr.success("PRESCRIPTION CREATED", { closeButton: true, timeOut: 3000, progressBar: true });
                    setTimeout(function () {
                        location.reload();
                    }, 3000);
                }

            },
            error: function (response) {
                toastr.error('An error has occured. Try again later', { closeButton: true, timeOut: 3000, progressBar: true, allowHtml: true });
            }
        });
    });
});

