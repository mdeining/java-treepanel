package trees.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for controlling the node analysis of 
 * a node object. By default the toString()-method is
 * used for displaying a node. If the label-annotation is
 * used, these methods will supply the label. Only methods 
 * with no parameters and a returning object are allowed.
 * If more than one method is annotated, the label will be
 * made up of all results.
 * 
 * @author Marcus deininger
 *
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.METHOD)
public @interface Label {

}
