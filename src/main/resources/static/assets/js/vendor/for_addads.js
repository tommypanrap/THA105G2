//輪播廣告選取照片及預覽
function imageSelected() {

	var input = document.getElementById('hiddenFileInput');
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			var previewArea = document.getElementById('adImagePreviewArea');
			previewArea.style.backgroundImage = 'url(' + e.target.result + ')';
			previewArea.style.backgroundSize = 'contain';
			previewArea.style.backgroundPosition = 'center';
		};
		reader.readAsDataURL(input.files[0]);
	};
};

document.addEventListener('DOMContentLoaded', (event) => {
	// 獲取今天的日期，並設定為廣告開始時間和結束時間的最小值
	let today = new Date().toISOString().split('T')[0];
	document.getElementById('adcStartDate').setAttribute('min', today);
	document.getElementById('adcEndDate').setAttribute('min', today);

	// 監聽開始時間的變化，來設定結束時間的最小值
	document.getElementById('adcStartDate').addEventListener('change', function() {
		// 檢查選擇的開始時間是否大於或等於今天
		let startValue = this.value;
		let minEndDate = startValue >= today ? startValue : today;

		// 將結束時間的最小值設定為開始時間或今天的日期，取決於哪個更晚
		document.getElementById('adcEndDate').setAttribute('min', minEndDate);
	});
//		前端驗證欄位
	function validateForm() {
		var fileInput = document.getElementById('hiddenFileInput');
		var startDateInput = document.getElementById('adcStartDate');
		var endDateInput = document.getElementById('adcEndDate');
		var dateError = document.getElementById('dateError');

		// 驗證圖片是否已選擇
		if (fileInput.files.length === 0) {
			alert('請選擇圖片！');
			return false;
		}

		// 驗證開始日期是否已選擇
		if (!startDateInput.value) {
			dateError.style.display = 'block';
			return false;
		}

		// 驗證結束日期是否已選擇
		if (!endDateInput.value) {
			dateError.style.display = 'block';
			return false;
		}
		// 驗證通過
		return true;
	}

	// JavaScript函數來計算廣告時間和價格
	function calculateAdPrice() {
		// 從前端獲取開始和結束日期
		const startDate = document.getElementById('adcStartDate').value;
		console.log("選開始時間")
		const endDate = document.getElementById('adcEndDate').value;
		console.log("選結束時間")

		// 確認開始和結束日期都已被選擇
		if (startDate && endDate) {
			const start = new Date(startDate);
			const end = new Date(endDate);
			const duration = (end - start) / (1000 * 60 * 60 * 24) + 1; // 加1因為包含開始那天
			const adDayPrice = 100; // 應該從後端獲取廣告每日價格
			const totalPrice = duration * adDayPrice;
			document.getElementById('adTotalPrice').textContent = `訂單總價： ${totalPrice}`;
			console.log("給p標籤值")
			document.getElementById('totalPriceField').value = totalPrice;
			console.log("給input標籤值")

		} else {
			// 如果日期沒有被選擇，則不顯示總價格
			document.getElementById('adTotalPrice').textContent = '請選擇開始和結束日期';
			//document.getElementById('totalPriceField').value = '';
		}
	}

	// 監聽日期變化
	document.getElementById('adcStartDate').addEventListener('change', calculateAdPrice);
	document.getElementById('adcEndDate').addEventListener('change', calculateAdPrice);

	// 初始化時也計算一次價格
	calculateAdPrice();

});

