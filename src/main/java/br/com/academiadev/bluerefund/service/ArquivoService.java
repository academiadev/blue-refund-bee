package br.com.academiadev.bluerefund.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.academiadev.bluerefund.dto.ArquivoDTO;

@Service
public class ArquivoService {

	private static String UPLOADED_FOLDER = "src//main//resources";
	private final Path rootLocation = Paths.get("");

	public ArquivoDTO upload(MultipartFile file, RedirectAttributes redirectAttributes){
		ArquivoDTO dto = new ArquivoDTO();
		
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return null;
		}
		
		try {
		
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			dto.setUrl(path.toString());
			Files.write(path, bytes);
		
		
		} catch (IOException e) {
		}
		
		
		return dto;
	}
	
	public Resource download(ArquivoDTO dto) {
		try {
			Path file = rootLocation.resolve(dto.getUrl());
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

}
