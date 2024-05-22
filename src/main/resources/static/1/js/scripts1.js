// file: password_visibility.js

function togglePasswordVisibility(event) {
    event.preventDefault(); // Ngăn chặn sự kiện mặc định của nút con mắt
    var passwordInput = event.target.previousElementSibling;
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        event.target.textContent = "👁️";
    } else {
        passwordInput.type = "password";
        event.target.textContent = "👁️";
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const tableRows = document.querySelectorAll("#dataTable tbody tr");
    const rowsPerPage = 10;
    let currentPage = 0;
    const totalPages = Math.ceil(tableRows.length / rowsPerPage);
    const updateTvForm = document.getElementById('updateTvForm');
    const updateTvFormContent = document.getElementById('updateTvFormContent');
    const checkboxes = document.querySelectorAll('#dataTable tbody tr td input[type="checkbox"]');
    const TvButton = document.getElementById('TvButton');
    const pageNumbersContainer = document.getElementById('pageNumbers');
    const maThietBiMuonInput = document.getElementById('maThietBiMuon');
    const closeFormButton = document.getElementById('closeFormButton');

    closeFormButton.addEventListener('click', function () {
        hideupdateTvForm();
    });

    function showPage(page) {
        const startIndex = page * rowsPerPage;
        const endIndex = Math.min(startIndex + rowsPerPage, tableRows.length);

        for (let i = 0; i < tableRows.length; i++) {
            if (i >= startIndex && i < endIndex) {
                tableRows[i].style.display = "table-row";
            } else {
                tableRows[i].style.display = "none";
            }
        }
    }

    function updatePageNumbers() {
        pageNumbersContainer.innerHTML = '';
        for (let i = 1; i <= totalPages; i++) {
            createPageNumberButton(i);
        }
    }

    function createPageNumberButton(pageNumber) {
        const pageNumberButton = document.createElement('button');
        pageNumberButton.textContent = pageNumber;
        pageNumberButton.addEventListener('click', function () {
            currentPage = pageNumber - 1;
            showPage(currentPage);
        });
        pageNumbersContainer.appendChild(pageNumberButton);
    }

    function updateTvButtonStatus() {
        let checkedCount = 0;
        checkboxes.forEach(function (checkbox) {
            if (checkbox.checked) {
                checkedCount++;
            }
        });
        TvButton.disabled = checkedCount !== 1;
    }

    function showupdateTvForm() {
        updateTvForm.style.display = 'block';
    }

    function hideupdateTvForm() {
        updateTvForm.style.display = 'none';
    }

    function getSelectedThietBi() {
        const selectedThietBi = [];
        checkboxes.forEach(function (checkbox, index) {
            if (checkbox.checked) {
                const maThietBi = tableRows[index].querySelectorAll('td')[2].textContent;
                selectedThietBi.push(maThietBi);
            }
        });
        return selectedThietBi.join(', ');
    }

    function updateMaThietBiMuonInput() {
        maThietBiMuonInput.value = getSelectedThietBi();
    }

    TvButton.addEventListener('click', function () {

        if (TvButton.disabled === false) {
            showupdateTvForm();
            updateMaThietBiMuonInput();
        }
    });

    // Sự kiện để ẩn form khi click ra ngoài form
    window.addEventListener('click', function (event) {
        if (event.target === updateTvForm) {
            hideupdateTvForm();
        }
    });

    showPage(currentPage);

    document.getElementById("nextPage").addEventListener("click", function () {
        if (currentPage < totalPages - 1) {
            currentPage++;
            showPage(currentPage);
        }
    });

    document.getElementById("prevPage").addEventListener("click", function () {
        if (currentPage > 0) {
            currentPage--;
            showPage(currentPage);
        }
    });

    const prevButton = document.getElementById('prevPage');
    const nextButton = document.getElementById('nextPage');

    updatePageNumbers();
    updateTvButtonStatus();

    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('click', function () {
            updateTvButtonStatus();
            updateMaThietBiMuonInput();
        });
    });

    prevButton.addEventListener('click', function () {
        if (currentPage > 0) {
            currentPage--;
            showPage(currentPage);
        }
    });

    nextButton.addEventListener('click', function () {
        if (currentPage < totalPages - 1) {
            currentPage++;
            showPage(currentPage);
        }
    });
});

        document.getElementById('TvButton').addEventListener('click', function () {
            // Lấy ra checkbox đã chọn
            const selectedCheckbox = document.querySelector('#dataTable tbody tr input[type="checkbox"]:checked');
            if (selectedCheckbox) {
                // Lấy ra hàng chứa checkbox đã chọn
                const selectedRow = selectedCheckbox.closest('tr');

                // Lấy ra các ô dữ liệu trong hàng đã chọn
                const cells = selectedRow.querySelectorAll('td');

                // Lấy giá trị của trường mật khẩu bằng ID
                const passWord = document.getElementById('passWord1').value;

                // Lấy ra các giá trị từ các ô dữ liệu
                const maThanhVien = cells[1].textContent;
                const tenThanhVien = cells[2].textContent;
                const khoa = cells[3].textContent;
                const nganh = cells[4].textContent;
                const sdt = cells[5].textContent;
                const email = cells[7].textContent;

                // Điền các giá trị vào các trường trong form chỉnh sửa
                document.getElementById('maThanhVien').value = maThanhVien;
                document.getElementById('hoTen').value = tenThanhVien;
                document.getElementById('khoa').value = khoa;
                document.getElementById('nganh').value = nganh;
                document.getElementById('sdt').value = sdt;
                document.getElementById('passWord').value = passWord;
                document.getElementById('email').value = email;

                // Hiển thị form chỉnh sửa
                document.getElementById('updateTvForm').style.display = 'block';
            } else {
                // Hiển thị thông báo nếu không có thành viên nào được chọn
                alert('Vui lòng chọn một thành viên để sửa.');
            }
        });

        document.getElementById('togglePassword').addEventListener('click', function () {
            // Lấy trường password
            const passwordInput = document.getElementById('passWord');
            // Lấy biểu tượng con mắt
            const eyeIcon = document.getElementById('eyeIcon');

            // Thay đổi kiểu của trường password từ password sang text và ngược lại
            if (passwordInput.type === "password") {
                passwordInput.type = "text";
                eyeIcon.classList.remove('fa-eye');
                eyeIcon.classList.add('fa-eye-slash');
            } else {
                passwordInput.type = "password";
                eyeIcon.classList.remove('fa-eye-slash');
                eyeIcon.classList.add('fa-eye');
            }
        });
   document.addEventListener('DOMContentLoaded', function() {
         document.getElementById('importExcelButton').addEventListener('click', function() {
             document.getElementById('fileInput').click();
         });

         document.getElementById('fileInput').addEventListener('change', function() {
             var file = this.files[0];
             if (file) {
                 var formData = new FormData();
                 formData.append('excelBtn', file);

                 fetch('/thanhVienExcel', {
                     method: 'POST',
                     body: formData
                 })
                 .then(response => response.json())
                 .then(data => {
                     if (data.success) {
                         alert('File uploaded and processed successfully');
                     } else {
                         alert('An error occurred: ' + data.message);
                     }
                 })
                 .catch(error => {
                     alert('An unexpected error occurred: ' + error.message);
                 });
             }
         });
     });

document.addEventListener('DOMContentLoaded', function () {

    document.getElementById('addMemberButton').addEventListener('click', function () {
        document.getElementById('addTvForm').style.display = 'block';
    });

    document.getElementById('closeAddFormButton').addEventListener('click', function () {
        document.getElementById('addTvForm').style.display = 'none';
    });

    document.getElementById('togglePassword').addEventListener('click', function () {
        const passwordField = document.getElementById('passWord');
        const eyeIcon = document.getElementById('eyeIcon');
        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            eyeIcon.classList.remove('fa-eye');
            eyeIcon.classList.add('fa-eye-slash');
        } else {
            passwordField.type = 'password';
            eyeIcon.classList.remove('fa-eye-slash');
            eyeIcon.classList.add('fa-eye');
        }
    });

    document.getElementById('toggleAddPassword').addEventListener('click', function () {
        const passwordField = document.getElementById('addPassWord');
        const eyeIcon = document.getElementById('addEyeIcon');
        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            eyeIcon.classList.remove('fa-eye');
            eyeIcon.classList.add('fa-eye-slash');
        } else {
            passwordField.type = 'password';
            eyeIcon.classList.remove('fa-eye-slash');
            eyeIcon.classList.add('fa-eye');
        }
    });
});
document.addEventListener('DOMContentLoaded', function () {
    const deleteButton = document.getElementById('deleteMemberButton');
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');

    // Function to check if at least one checkbox is selected
    function checkCheckboxes() {
        let isChecked = false;
        checkboxes.forEach((checkbox) => {
            if (checkbox.checked) {
                isChecked = true;
            }
        });
        deleteButton.disabled = !isChecked;
    }

    // Add event listeners to checkboxes
    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', checkCheckboxes);
    });

    // Initial check
    checkCheckboxes();
});

// edit member
    document.getElementById('save-btn').addEventListener('click', function(event) {
    // Ngăn chặn hành vi mặc định của nút (chẳng hạn khi nhấn nút trang web không được tải lại)
    event.preventDefault();

    // Lấy dữ liệu từ các trường input trong form
    var maTV = document.getElementById('maThanhVien').value;
    var tenTV = document.getElementById('hoTen').value;
    var khoa = document.getElementById('khoa').value;
    var nganh = document.getElementById('nganh').value;
    var sdt = document.getElementById('sdt').value;
    var password = document.getElementById('passWord').value;
    var email = document.getElementById('email').value;

    // Tạo một đối tượng chứa dữ liệu để gửi đi
    var memberData = {
        maTV: maTV,
        tenTV: tenTV,
        khoa: khoa,
        nganh: nganh,
        sdt: sdt,
        password: password,
        email: email
    };

    // Gửi dữ liệu đến endpoint "/editMember" bằng phương thức POST
    fetch('/editMember', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(memberData)
    })
    .then(response => response.json())
    .then(data => {
        // Xử lý phản hồi từ máy chủ
       // Xử lý phản hồi từ máy chủ
       if (data.success) {
           // Nếu cập nhật thành công, hiển thị thông báo thành công và ẩn form chỉnh sửa
           alert(data.message);
           hideEditForm();
           // Reload lại trang sau khi đã cập nhật thành công
           window.location.reload();
       } else {
           // Nếu cập nhật không thành công, hiển thị thông báo lỗi
           alert(data.message);
       }
    })
    .catch(error => {
        // Xử lý lỗi nếu có
        console.error('Error:', error);
    });
});