package com.rev_connect_api.services;

import com.rev_connect_api.enums.MediaType;
import com.rev_connect_api.models.Media;
import com.rev_connect_api.repositories.MediaRepository;
import com.rev_connect_api.util.TimestampUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaService {

    // File url to upload media, starts at root of project (rev-connect-api)
    private static final String attachmentsDirectory = "src/main/resources/public/attachments";

    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    // The method to save the actual Media entity to database
    @Transactional
    public Media saveMedia(MultipartFile file, BigInteger postId, Timestamp createdAt) {
        String path;
        try {
            path = saveFile(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        MediaType mediaType = file.getContentType().startsWith("image") ? MediaType.IMAGE : MediaType.VIDEO;
        Media media = Media.builder()
                .postId(postId)
                .createdAt(createdAt)
                .mediaType(mediaType)
                .mediaUrl(path)
                .build();
        return mediaRepository.save(media);
    }

    public Media getMediaByPostId(BigInteger postId) {
        Optional<Media> optionalMedia = mediaRepository.getMediaByPostId(postId);
        return optionalMedia.orElse(null);
    }

    @Transactional
    public boolean deleteMediaByPostId(BigInteger postId) {
        Media media = getMediaByPostId(postId);
        if(media == null) {
            return false;
        }
        mediaRepository.deleteMediaByPostId(postId);
        try {
            File file = new File(attachmentsDirectory + "/" + media.getMediaUrl());
            return file.delete();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Saves attachments locally, should probably switch out implementation in production
    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        Path destination = Paths.get(attachmentsDirectory)
                .resolve(Paths.get(fileName))
                .normalize().toAbsolutePath();

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
