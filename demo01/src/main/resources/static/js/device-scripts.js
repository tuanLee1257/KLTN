$(document).ready(function () {
  $("#FORM_TIME_INPUT").datepicker();
  $("#deviceList").DataTable({
    pageLength: 5,
    ajax: {
      url: "/api/devices",
      dataSrc: "",
    },

    columns: [
      {
        title: "Id",
        data: "id",
      },
      {
        title: "Name",
        data: "name",
      },
      {
        title: "Image",
        data: "image",
      },
      {
        title: "Date ",
        data: "dateAcquired",
      },
      {
        title: "QR",
        data: "qr.name",
      },
      {
        title: "Status",
        data: "status",
      },
      {
        title: "Action",
        data: "id",
        render: function (data, type, row) {
          return `
              <button
                id="buttonEdit"
                type="button"
                class="btn btn-secondary open-modal"
                data-id="${data}"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  class="bi bi-pencil"
                  viewBox="0 0 16 16"
                >
                  <path d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001zm-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z"></path>
                </svg>
              </button>
            `;
        },
      },
    ],
  });
  $("#editForm").submit(function (event) {
    var device = {
      id: $("#FORM_DEVICE_ID").val(),
      name: $("#FORM_DEVICE_NAME").val(),
      dateAcquired: $("#FORM_TIME_INPUT").val(),
      status: $("#FORM_STATUS").val(),
    };

//    var fileInput = document.getElementById("FORM_IMAGE");
//    var file = fileInput.files[0]; // Lấy tệp đầu tiên (nếu có)

    $.ajax({
      type: "POST",
      url: "/device/update/" + device.id,
      data: { device },
      dataType: "json",
      encode: true,
    })
      .error(function (XMLHttpRequest, textStatus, errorThrown) {
        alert("some error");
      })
      .done(function (data) {
        location.reload();
      });

    event.preventDefault();
  });
  $(".form-exit").on("click", function () {
    $("#editModal").modal("hide");
  });
});

$(document).on("click", "#buttonEdit", function () {
  var dataId = $(this).data("id"); // Lấy giá trị từ data-id của button
  var device = devices.find(function (device) {
    return device.id == dataId;
  });
  $("#FORM_DEVICE_ID").val(device.id);
  $("#FORM_DEVICE_NAME").val(device.name);
  $("#FORM_TIME_INPUT").val(device.dateAcquired);
  $("#FORM_STATUS").val(device.status).change();

  $("#editModal").modal("show");
});
