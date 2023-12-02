package com.sjb.sjbProject.controller;

import com.sjb.sjbProject.bean.Room;
import com.sjb.sjbProject.bean.RoomPicture;
import com.sjb.sjbProject.service.RoomPictureService;
import com.sjb.sjbProject.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@RestController
@CrossOrigin
public class RoomPictureController {

	@Autowired
	private RoomService rService;

	@Autowired
	private RoomPictureService roomPictureService;

	@PostMapping("/room/addphoto")
	public String postAddSpots(@RequestParam(name = "hotelID") Integer hotelID,
								@RequestParam(name = "productID")String productID,
								@RequestPart("photofile")MultipartFile[] file) throws IOException {

		System.out.println("#####################NULL???"+hotelID+productID);

		Room room=rService.findRoomById(productID, hotelID);

		List<RoomPicture> photos=new LinkedList<>();

		for (MultipartFile oneFile : file) {
			RoomPicture roomPicture=new RoomPicture();

			roomPicture.setProductID(productID);
			roomPicture.setImagePath(oneFile.getBytes());
			roomPicture.setRoom(room);

			photos.add(roomPicture);
		}

		room.setRoomPictures(photos);

		rService.insertRoom(room);

		return "redirect:/room/findAllRoom";
	}

//	@GetMapping("/room/getphoto")
//	public List<RoomPicture> findAllRoomPictureByProductID(@RequestParam(name = "productID")String productID){
//		return roomPictureService.findAllRoomPictureByProductID(productID);
//	}

	@GetMapping("/room/getphoto")
	public ResponseEntity<List<byte[]>> findAllRoomPictureByProductID(@RequestParam(name = "productID")String productID){
		List<RoomPicture> list = roomPictureService.findAllRoomPictureByProductID(productID);
		if(list == null){
			return ResponseEntity.noContent().build();
		}

		List<byte[]> photos =new ArrayList<>();
		for (RoomPicture photo : list) {
			photos.add(photo.getImagePath());
		}
		return ResponseEntity.ok(photos);
	}

	@GetMapping("/room/images/{imgID}")
	public ResponseEntity<byte[]> getRoomPicture(@PathVariable Integer imgID) {

		RoomPicture result = roomPictureService.findByimgID(imgID);
		if (result == null){
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(result.getImagePath());
	}
	@DeleteMapping("/room/images/delete")
	public Integer deleteByimgID(@RequestParam(name = "imgID")Integer imgID){
		RoomPicture result = roomPictureService.findByimgID(imgID);
		if (result == null){
			return null;
		}
		return  roomPictureService.deleteByimgID(imgID);
	}
}
