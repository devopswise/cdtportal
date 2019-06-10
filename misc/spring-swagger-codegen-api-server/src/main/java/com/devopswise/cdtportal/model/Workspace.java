package com.devopswise.cdtportal.model;

import java.util.Objects;
import com.devopswise.cdtportal.model.Tag;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A workspace based on Theia ide
 */
@ApiModel(description = "A workspace based on Theia ide")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-10T16:50:20.069Z")

public class Workspace   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("owner")
  private String owner = null;

  @JsonProperty("git_url")
  private String gitUrl = null;

  @JsonProperty("tags")
  private List<Tag> tags = null;

  @JsonProperty("description")
  private String description = null;

  public Workspace id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Workspace name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "Helloworld", required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Workspace owner(String owner) {
    this.owner = owner;
    return this;
  }

   /**
   * Get owner
   * @return owner
  **/
  @ApiModelProperty(value = "")


  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public Workspace gitUrl(String gitUrl) {
    this.gitUrl = gitUrl;
    return this;
  }

   /**
   * Get gitUrl
   * @return gitUrl
  **/
  @ApiModelProperty(example = "git://hrweb@gitea", value = "")


  public String getGitUrl() {
    return gitUrl;
  }

  public void setGitUrl(String gitUrl) {
    this.gitUrl = gitUrl;
  }

  public Workspace tags(List<Tag> tags) {
    this.tags = tags;
    return this;
  }

  public Workspace addTagsItem(Tag tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<Tag>();
    }
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * Get tags
   * @return tags
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public Workspace description(String description) {
    this.description = description;
    return this;
  }

   /**
   * definition of project, like Human Resources Web Portal
   * @return description
  **/
  @ApiModelProperty(value = "definition of project, like Human Resources Web Portal")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Workspace workspace = (Workspace) o;
    return Objects.equals(this.id, workspace.id) &&
        Objects.equals(this.name, workspace.name) &&
        Objects.equals(this.owner, workspace.owner) &&
        Objects.equals(this.gitUrl, workspace.gitUrl) &&
        Objects.equals(this.tags, workspace.tags) &&
        Objects.equals(this.description, workspace.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, owner, gitUrl, tags, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Workspace {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    gitUrl: ").append(toIndentedString(gitUrl)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

