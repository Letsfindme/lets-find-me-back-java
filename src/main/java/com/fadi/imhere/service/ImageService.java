package com.fadi.imhere.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.fadi.imhere.model.Image;
import com.fadi.imhere.repository.ImageRepositoy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Fadi NOUFAL
 */
@Service
public class ImageService {

    private static String UPLOAD_ROOT = "/Users/foxy/Documents/lets-find-me/back/src/main/resources/images/";
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class.getName());

    private ImageRepositoy imageRepositoy;
    private ResourceLoader resourceLoader;

    @Autowired
    public ImageService(ImageRepositoy imageRepositoy, ResourceLoader resourceLoader){
        this.imageRepositoy = imageRepositoy;
        this.resourceLoader = resourceLoader;
    }

    public Resource findOneImage(String filename) {
		return resourceLoader.getResource("file:" + UPLOAD_ROOT + filename + ".jpg");
	}

    public byte[] byteOneImage(String filename) {
        byte[] image = null;
        try {
            image = Files.readAllBytes(Paths.get(UPLOAD_ROOT+filename));
        } catch (IOException e) {
            LOGGER.error("Can not read the file with file name : '{}'", filename);
        }
        return image;
    }

    public void createImage(MultipartFile file, String id) throws IOException {
        String fileName = UPLOAD_ROOT + File.separator + id + ".jpg";
		if (!file.isEmpty()) {
			Files.copy(file.getInputStream(), Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
			imageRepositoy.save(new Image(id + ".jpg"));
		}
    }

    public void deleteImage(String filename) throws IOException {

//		final Image byName = imageRepositoy.findByName(filename);
//		imageRepositoy.delete(byName);
		Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename+ ".jpg"));
	}
}
