package ict373.assignment2.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * <strong>WindowEvent class</strong>
 * 
 * <p>WindowEvent handles event related to the main application window such as opening, closing, minimizing
 * the window.</p>
 * 
 * @author nwnisworking
 * @date 15/3/2026
 * @filename WindowEvent.java
 */
public class WindowEvent extends Event{
	/**
	 * The base event type for all window events.
	 */
	public static final EventType<WindowEvent> ANY = new EventType<>(Event.ANY, "WINDOW_EVENT");

	/**
	 * Event type for when the window is minimized.
	 */
	public static final EventType<WindowEvent> MINIMIZE = new EventType<>(ANY, "WINDOW_MINIMIZE");

	/**
	 * Event type for when the window is maximized.
	 */
	public static final EventType<WindowEvent> MAXIMIZE = new EventType<>(ANY, "WINDOW_MAXIMIZE");

	/**
	 * Event type for when the window is closed.
	 */
	public static final EventType<WindowEvent> CLOSE = new EventType<>(ANY, "WINDOW_CLOSE");

	/**
	 * Event type for when the window is opened.
	 */
	public static final EventType<WindowEvent> OPEN = new EventType<>(ANY, "WINDOW_OPEN");

	/**
	 * Constructor for WindowEvent.
	 * @param event_type The type of the window event.
	 */
	public WindowEvent(EventType<? extends WindowEvent> event_type){
		super(event_type);
	}
}