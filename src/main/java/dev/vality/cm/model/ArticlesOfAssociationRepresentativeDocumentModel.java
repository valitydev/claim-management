package dev.vality.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("articles_of_association")
public class ArticlesOfAssociationRepresentativeDocumentModel extends RepresentativeDocumentModel {

}
