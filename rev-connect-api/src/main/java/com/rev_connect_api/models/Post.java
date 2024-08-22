package com.rev_connect_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;

/**
 * Entity class representing a post in the RevConnect application.
 * Each post is associated with a user and contains content, a title, and timestamps for when it was created and last updated.
 */
@Entity
@Table(name = "posts")
public class Post {

    /**
     * The unique identifier for the post.
     */
    @Id
    private Long id;

    /**
     * The user who created the post.
     * This is a many-to-one relationship, with the user being the owner of many posts.
     * The {@link JsonIgnore} annotation prevents the user object from being serialized in the JSON response.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = false)
    @JsonIgnore
    private User user;

    /**
     * The content of the post. This field cannot be blank.
     */
    @NotBlank
    @Column(nullable = false)
    private String content;

    /**
     * The timestamp when the post was created.
     * This field is automatically set when the post is persisted.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp when the post was last updated.
     * This field is automatically updated whenever the post is updated.
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * The title of the post.
     */
    private String title;

    /**
     * Automatically sets the creation timestamp before the entity is persisted.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Automatically sets the updated timestamp before the entity is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Constructs a new post with the specified user, content, creation timestamp, update timestamp, and title.
     *
     * @param user      the user who created the post.
     * @param content   the content of the post.
     * @param createdAt the timestamp when the post was created.
     * @param updatedAt the timestamp when the post was last updated.
     * @param title     the title of the post.
     */
    public Post(User user, String content, LocalDateTime createdAt, LocalDateTime updatedAt, String title) {
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
    }

    /**
     * Constructs a new post with the specified user, content, creation timestamp, and update timestamp.
     *
     * @param user      the user who created the post.
     * @param content   the content of the post.
     * @param createdAt the timestamp when the post was created.
     * @param updatedAt the timestamp when the post was last updated.
     */
    public Post(User user, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Default constructor for JPA.
     */
    public Post() {
    }

    /**
     * Returns the unique identifier of the post.
     *
     * @return the ID of the post.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the post.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user who created the post.
     *
     * @return the user who created the post.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who created the post.
     *
     * @param user the user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the content of the post.
     *
     * @return the content of the post.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the post.
     *
     * @param content the content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the title of the post.
     *
     * @return the title of the post.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the post.
     *
     * @param title the title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the timestamp when the post was created.
     *
     * @return the creation timestamp of the post.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when the post was created.
     *
     * @param createdAt the creation timestamp to set.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the timestamp when the post was last updated.
     *
     * @return the last updated timestamp of the post.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the timestamp when the post was last updated.
     *
     * @param updatedAt the last updated timestamp to set.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
