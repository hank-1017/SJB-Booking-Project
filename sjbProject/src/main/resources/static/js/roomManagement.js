window.onload = function() {

	var roomPicturesEmpty = /*[[${room.roomPictures.isEmpty()}]]*/ false;

	/* 获取房间图片列表 */
	var roomPictures = /*[[${room.roomPictures}]]*/[];

	/* 显示房间图片 */
	function showRoomPictures() {
		var imageContainer = document.getElementById("image-container");

		if (!roomPicturesEmpty) {
			roomPictures.forEach(function(roomPicture) {
				if (roomPicture != null && roomPicture.imagePath) {
					var img = document.createElement("img");
					img.src = roomPicture.imagePath;
					img.alt = /*[[${room.productName}]]*/ "";
					imageContainer.appendChild(img);
				}
			});
		} else {
			var defaultImg = document.createElement("img");
			defaultImg.src = "/img/roomPictureDefault.jpg";
			defaultImg.alt = /*[[${room.productName}]]*/ "";
			imageContainer.appendChild(defaultImg);
		}
	}

	/* ########################### 上傳圖片 ########################### */
	var fileInput = document.getElementById("photofile");

	fileInput.addEventListener("change", function() {

		var form = document.getElementById("upload-form");

		form.submit();
	});

	/* ########################### 上下架開關顯示 ########################### */
	var enableSwitch = document.getElementById("enableSwitch").value;
	var enableSwitchLabel = document.getElementById("enableSwitchLabel");

	enableSwitch === "true" ? enableSwitchLabel.innerHTML = "上架中" : enableSwitchLabel.innerHTML = "已下架"

	/* ########################### 刪除的alert ########################### */
	
	let deleteBtn = document.getElementById("deleteBtn");
	deleteBtn.addEventListener("submit", function(event){
		event.preventDefault(); // 阻止默認提交
		
		const confirmation = confirm("是否確定刪除？");
		
		if (confirmation) {
			document.getElementById("deleteForm").submit();
		}
	    
				
	})

	/*let deleteBtn = document.getElementById("deleteBtn");
	deleteBtn.preventDefault(); // 阻止默认提交
	function confirmDelete(event) {
		console.log('132');
		const confirmation = confirm("是否確定刪除？");
		if (confirmation) {
			document.getElementById("deleteForm").submit();
		}
	}*/

}
