package com.tooooolazy.gwt.widgets.shared;

/**
 * All components/elements that we want to be multilingual must implement this interface.<br>
 * <ul>Also:
 * <li><code>addToHandler</code> should be called inside their constructor on <code>onAttach</code>.</li>
 * <li>Similarly, <code>removeFromHandler</code> should be called inside <code>onDetach</code>.</li>
 * </ul>
 * Both of them do not have to be implemented if the parent element calls <code>setTitles</code> on it directly.
 * <p>When used by a <code>BaseEntryPoint</code> object, all <code>Multilingual</code> based components should be created through <code>BaseEntryPoint.createDependentUI()</code> function to ensure that the Dictionary is loaded</p>
 * TODO: might be a good idea to have a <code>getDictionary()</code> function, so that the result is more abstract and not dependent on <code>BaseEntryPoint</code>
 * @author tooooolazy
 *
 */
public interface Multilingual {
	public void setTitles();
	public String getMessage(String key);
	/**
	 * there should be a list of multilingual items somewhere (a handler), so an iterator could call
	 * the <code>setTitles</code> on each one when language changes.<br>
	 * This should be called either in a constructor or in onAttach.
	 */
	public void addToHandler();
	/**
	 * This should be called in onDetach. It should remove the item from the multilingual list.
	 */
	public void removeFromHandler();
}
