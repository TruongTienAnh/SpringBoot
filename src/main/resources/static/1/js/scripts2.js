document.addEventListener("DOMContentLoaded", function () {
    const tableRows = document.querySelectorAll("#dataTable tbody tr");
    const rowsPerPage = 10;
    let currentPage = 0;
    const totalPages = Math.ceil(tableRows.length / rowsPerPage);
    const upTbButton = document.getElementById('upTbButton');
    const upTbButtonContent = document.getElementById('upTbButtonContent');
    const checkboxes = document.querySelectorAll('#dataTable tbody tr td input[type="checkbox"]');
    const TbButton = document.getElementById('TbButton');
    const pageNumbersContainer = document.getElementById('pageNumbers');
    const maThietBiMuonInput = document.getElementById('maThietBiMuon');
    const closeFormButton = document.getElementById('closeFormButton');

    closeFormButton.addEventListener('click', function () {
        hideupTbButton();
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

    function updateTbButtonStatus() {
        let checkedCount = 0;
        checkboxes.forEach(function (checkbox) {
            if (checkbox.checked) {
                checkedCount++;
            }
        });
        TbButton.disabled = checkedCount !== 1;
    }
    

    function showupTbButton() {
        upTbButton.style.display = 'block';
    }

    function hideupTbButton() {
        upTbButton.style.display = 'none';
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

    TbButton.addEventListener('click', function () {
        if (TbButton.disabled === false) {
            showupTbButton();
            updateMaThietBiMuonInput();
        }
    });

    // Sự kiện để ẩn form khi click ra ngoài form
    window.addEventListener('click', function (event) {
        if (event.target === upTbButton) {
            hideupTbButton();
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
    updateTbButtonStatus();

    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('click', function () {
            updateTbButtonStatus();
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
document.getElementById('TbButton').addEventListener('click', function () {
    // Lấy ra checkbox đã chọn
    const selectedCheckbox = document.querySelector('#dataTable tbody tr input[type="checkbox"]:checked');
    if (selectedCheckbox) {
        // Lấy ra hàng chứa checkbox đã chọn
        const selectedRow = selectedCheckbox.closest('tr');
        
        // Lấy ra các ô dữ liệu trong hàng đã chọn
        const cells = selectedRow.querySelectorAll('td');

        // Lấy ra các giá trị từ các ô dữ liệu
        const maThietBi = cells[2].textContent;
        const tenThietBi = cells[3].textContent;
        const moTa = cells[4].textContent;
        let tinhTrang = cells[5].textContent;

        // Xác định tình trạng hiện tại của thiết bị
        if (tinhTrang === "Hỏng") {
            tinhTrang = "hong";
        } else if (tinhTrang === "Sẵn Sàng") {
            tinhTrang = "sanSang";
        }

        // Điền các giá trị vào các trường trong form chỉnh sửa
        document.getElementById('maThietBi').value = maThietBi;
        document.getElementById('tenThietBi').value = tenThietBi;
        document.getElementById('moTa').value = moTa;
        document.getElementById('tinhTrang').value = tinhTrang;

        // Hiển thị form chỉnh sửa
        document.getElementById('updateTvForm').style.display = 'block';
    } else {
        // Hiển thị thông báo nếu không có thành viên nào được chọn
        alert('Vui lòng chọn một thành viên để sửa.');
    }
});
$(document).ready(function() {
    // Ẩn form thêm thiết bị khi trang được tải
    $('#addTbButtonContent').hide();

    // Xử lý sự kiện khi nhấp vào nút "Thêm Thiết Bị"
    $('#addDeviceButton').click(function() {
        // Ẩn form sửa thiết bị (nếu đang hiển thị)
        $('#upTbButton').hide();
        // Hiển thị form thêm thiết bị
        $('#addTbButtonContent').show();
    });

    // Xử lý sự kiện khi nhấp vào nút đóng form thêm thiết bị
    $('#closeAddFormButton').click(function() {
        // Ẩn form thêm thiết bị
        $('#addTbButtonContent').hide();
    });

    // Xử lý sự kiện khi nút "Lưu" trong form thêm thiết bị được nhấn
    $('#addTbForm').submit(function(e) {
        // Ngăn chặn hành động mặc định của form (tránh làm tải lại trang)
        e.preventDefault();
        // Thực hiện các hành động cần thiết khi lưu thông tin thiết bị mới
        // Ví dụ: Gửi yêu cầu AJAX để lưu dữ liệu vào cơ sở dữ liệu
        // Sau khi lưu, có thể hiển thị thông báo thành công và làm sạch form
        // Sau đó có thể ẩn form thêm thiết bị lại.
        // Ẩn form thêm thiết bị
        $('#addTbButtonContent').hide();
    });
});
// Lấy danh sách các ô chọn
const checkboxes = document.querySelectorAll('input[type="checkbox"]');

// Lấy nút "Xoá Thiết Bị"
const deleteButton = document.getElementById('deleteTbButton');

// Lặp qua từng ô chọn và thêm sự kiện click
checkboxes.forEach(checkbox => {
    checkbox.addEventListener('click', function() {
        // Kiểm tra nếu có ít nhất một ô chọn được chọn
        const atLeastOneChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

        // Kích hoạt hoặc vô hiệu hóa nút "Xoá Thiết Bị" dựa trên trạng thái của các ô chọn
        deleteButton.disabled = !atLeastOneChecked;
    });
});
