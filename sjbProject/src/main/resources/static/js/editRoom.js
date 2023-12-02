
window.onload = function () {

    let checkInTime=document.getElementById("checkInTime");
    let checkOutTime=document.getElementById("checkOutTime");
    checkInTime.innerHTML += '<option value="no">請選擇</option>';
    checkOutTime.innerHTML += '<option value="no">請選擇</option>';


    for (let hour = 0; hour < 24; hour++) {
        let formattedHour = String(hour).padStart(2, '0');
        /*
        checkInTime.innerHTML+= '<option value="' + formattedHour + ':00">'
        + formattedHour + ':00</option>';
        checkOutTime.innerHTML+= '<option value="' + formattedHour + ':00">'
        + formattedHour + ':00</option>';
        */
        let optionCheckIn = document.createElement("option");
	    optionCheckIn.value = formattedHour + ':00';
	    optionCheckIn.textContent = formattedHour + ':00';
	    
	    if((formattedHour + ':00') === checkInTime.value){
			optionCheckIn.selected = true;
		}
	    checkInTime.appendChild(optionCheckIn);
	
	    let optionCheckOut = document.createElement("option");
	    optionCheckOut.value = formattedHour + ':00';
	    optionCheckOut.textContent = formattedHour + ':00';
	    
	    if((formattedHour + ':00') === checkOutTime.value){
			optionCheckOut.selected = true;
		}
	    checkOutTime.appendChild(optionCheckOut);
        
    }
    // -------------------------------------------------------------------------------------
    
    let roomTypeArr = ['標準單人房', '標準雙人房', '標準套房', '豪華單人房', '豪華雙人房', '豪華套房', '海景單人房', '海景雙人房', '海景套房', '街景單人房', '街景雙人房', '街景套房', '家庭套房', '總統套房', '主題套房', '度假屋', '別墅', '木屋', '湖景房', '山景房', '泳池別墅', '豪華游艇套房', '沙灘小屋', '草原帳篷', '城堡套房', '露營車', '溫泉別墅', '河景小屋', '高空觀景房', '寒意小屋', '浪漫小屋', '花園別墅', '古典套房', '現代風格房', '鄉村風格房', '海灘別墅', '山莊', '城市景觀套房'];
	let productType= document.getElementById("productType");

	productType.innerHTML += '<option value="no">請選擇</option>';
		
	for (let i = 0; i < roomTypeArr.length; i++) { 
	    let option = document.createElement("option");
	    option.value = roomTypeArr[i];
	    option.textContent = roomTypeArr[i];
	    if(roomTypeArr[i] == productType.value){
			option.selected = true;
		}
		
	    productType.appendChild(option);
	    
    }
    
    // console.log(productType.value);
    
    // -------------------------------------------------------------------------------------
    let capacity= document.getElementById("capacity");
	capacity.innerHTML += '<option value="no">請選擇</option>';
	
	for (let i = 1; i <= 20; i++) { 
	    // capacity.innerHTML+= '<option value="' + i + '">' + i + '</option>';
	    let option = document.createElement("option");
	    option.value = i;
	    option.textContent = i ;
	    if(i == capacity.value){
			option.selected = true;
		}
	    
	    
	    capacity.appendChild(option);
    }
    // -------------------------------------------------------------------------------------
	// enableSwitchLabel的更換
	let enableSwitch = document.getElementById("enableSwitch");
	let enableSwitchLabel = document.getElementById("enableSwitchLabel");
	
	console.log(enableSwitch.checked);
	
	enableSwitch.addEventListener("change", function(){
		if (enableSwitch.checked) {
	        enableSwitchLabel.textContent = "上架中";
	    } else {
	        enableSwitchLabel.textContent = "已下架";
	    }
	})
	
	
	// -------------------------------------------------------------------------------------
	// 是否確定修改對話框
	let submitBtn = document.getElementById("submitBtn");
	submitBtn.addEventListener("click", function(event){
		event.preventDefault(); // 阻止默認提交
		
		const confirmation = confirm("是否確定修改？");
		
        if (confirmation) {
            document.getElementById("editForm").submit();
        }
        
				
	})



}