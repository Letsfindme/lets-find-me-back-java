package com.fadi.imhere.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import com.fadi.imhere.dtos.PostDto;
import com.fadi.imhere.service.ImageService;
import com.fadi.imhere.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

/**
 * @author Fadi NOUFAL
 */
@RestController
public class HomeController {
    private static final String BASE_PATH = "/images";
    private static final String FILENAME = "{filename:.+}";
    
    @Autowired
    private PostService postService;
    private final ImageService imageService;

	@Autowired
	public HomeController(ImageService imageService) {
		this.imageService = imageService;
    }
    
    @GetMapping(value = "/")
    protected String index(){
        return "Hello world";
    }

    @GetMapping(value = "/private")
    protected String privateArea(){
        return "Private area";
    }

    @GetMapping(value = "/findPaginated")
    protected Page<PostDto> findPaginated(@RequestParam("p") Integer page ,
                                          @RequestParam(value = "s", required = false)Integer size){
        Integer checkedSize = (size == null ? 4 : size);
        return  postService.findPaginated(checkedSize, page);
    }


	@GetMapping(value = BASE_PATH)
	public ResponseEntity<?> getOneImage(@RequestParam String id) {
		try {
			Resource file = imageService.findOneImage(id);
			return ResponseEntity.ok()
					.contentLength(file.contentLength())
					.contentType(MediaType.IMAGE_JPEG)
					.body(file);
		} catch (IOException e) {
			return ResponseEntity.badRequest()
					.body("Couldn't find " + id + " => " + e.getMessage());
		}
	}

	@GetMapping(value = BASE_PATH + "/" + FILENAME)
	public ResponseEntity<byte[]> getContentContent(@PathVariable String filename) {

		byte[] image = imageService.byteOneImage(filename);
		String contentType = "image/jpeg";
		ResponseEntity<byte[]> responseEntity;

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_DISPOSITION, "attachment; filename=" + filename);
		responseHeaders.add(CONTENT_TYPE,contentType );

		HttpStatus status = image != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		responseEntity = new ResponseEntity<>(image, responseHeaders, status);

		return responseEntity;
	}

	@PostMapping(value = BASE_PATH)
	public ResponseEntity<?> createFile(@RequestParam String id, @RequestParam("file") MultipartFile file, HttpServletRequest servletRequest) throws URISyntaxException {

		try {
			imageService.createImage(file, id);
			final URI locationUri = new URI(servletRequest.getRequestURL().toString() + "/")
					.resolve(file.getOriginalFilename() + "/raw");

//			return ResponseEntity.created(locationUri).build();
			System.out.println("passage");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
		}
	}

	@DeleteMapping(value = BASE_PATH + "/" + FILENAME)
	public ResponseEntity<?> deleteFile(@PathVariable String filename) {
		try {
			imageService.deleteImage(filename);
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body("Successfully deleted " + filename);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to delete " + filename + " => " + e.getMessage());
		}
	}
}
