package com.devopswise.cdtportal.model;

import java.util.Objects;
import com.devopswise.cdtportal.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.naming.Name;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Group
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-12-12T17:43:26.471Z")

public class Group   {
  @Id
  @JsonProperty("id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("members")
  private HashSet<Name> members = null;

  @JsonProperty("description")
  private String description = null;

  public Group id(Long id) {
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

  public Group name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Group members(HashSet<Name> members) {
    this.members = members;
    return this;
  }

  public void addMember(Name member) {
      if (this.members == null){
          this.members = new HashSet<>();
      }
      members.add(member);
  }

  public void removeMember(Name member) {
      members.remove(member);
  }
  
  /*
  public Group addMembersItem(Name membersItem) {
    if (this.members == null) {
      this.members = new ArrayList<Name>();
    }
    this.members.add(membersItem);
    return this;
  }
  */
   /**
   * Get members
   * @return members
  **/
  @ApiModelProperty(value = "")

  @Valid

  public HashSet<Name> getMembers() {
    return members;
  }

  public void setMembers(HashSet<Name> members) {
    this.members = members;
  }

  public Group description(String description) {
    this.description = description;
    return this;
  }

   /**
   * description about group
   * @return description
  **/
  @ApiModelProperty(example = "This group holds developers in HRWEB project", value = "description about group")


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
    Group group = (Group) o;
    return Objects.equals(this.id, group.id) &&
        Objects.equals(this.name, group.name) &&
        Objects.equals(this.members, group.members) &&
        Objects.equals(this.description, group.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, members, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Group {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    members: ").append(toIndentedString(members)).append("\n");
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

