/**
 * Package for collecting annotations controlling the processing
 * of recursive data structures.
 * <ul>
 * <li> <code>{@link trees.annotations.Ignore @Ignore}</code> forces the processor to ignore this annotated field.
 * <li> <code>{@link trees.annotations.Nodes @Nodes}</code> forces the processor to process only this annotated field. 
 * 		If no annotation is present, all recursive fields are processed.
 * <li> <code>{@link trees.annotations.Label @Label}</code> forces the processor to use the annotated method for getting a label string.
 *		The method must not have any parameters. If no annotation is present, <code>toString()</code> will be used.
 * </ul>
 * 
 * @author Marcus Deininger
 *
 */
package trees.annotations;