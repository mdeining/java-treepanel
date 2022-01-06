package trees.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * Annotation for controlling the node analysis of 
 * a node object. By default all references to objects of 
 * the same type are used for displaying. By using the Ignore-annotation
 * the annotated object will be excluded. This may be useful if you
 * have a tree-structure which points back to the parent.
 * 
 * @author Marcus deininger
 *
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.FIELD)
public @interface Ignore {

}
