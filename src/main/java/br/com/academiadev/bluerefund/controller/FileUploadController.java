package br.com.academiadev.bluerefund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.academiadev.bluerefund.dto.ArquivoDTO;
import br.com.academiadev.bluerefund.service.ArquivoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Arquivo")
@RestController
@RequestMapping("/arquivo")
public class FileUploadController {

	@Autowired
	private ArquivoService arquivoService;
	
    @ApiOperation(value = "Faz upload do arquivo e recebe a ulr em que ele foi salvo")
    @PostMapping("/upload") 
    public ArquivoDTO upload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        return arquivoService.upload(file, redirectAttributes);
    }
    
    @GetMapping("/download")
    @ApiOperation(value = "Faz download do arquivo")
    @ResponseBody
    public ResponseEntity<Resource> download(ArquivoDTO dto) {
    	Resource file = arquivoService.download(dto);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
    }


}
