/*
 * Copyright (c) 2002 Light Weight Java Game Library Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'Light Weight Java Game Library' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.lwjgl.examples;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Window;

/**
 * $Id$
 *
 * This is a <em>very basic</em> skeleton to init a game and run it.
 *
 * @author $Author$
 * @version $Revision$
 */
public class Game {
	
	/** Game title */
	public static final String GAME_TITLE = "My Game";
	
	/** Desired frame time */
	private static final float FRAMETIME = 1.0f / 60.0f;
	
	/** Exit the game */
	private static boolean finished;
	
	/**
	 * No constructor needed - this class is static
	 */
	private Game() {}
	
	/**
	 * Application init
	 * @param args Commandline args
	 */
	public static void main(String[] args) {
		try {
			init();
			run();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			Sys.alert(GAME_TITLE, "An error occured and the game will exit.");
		} finally {
			cleanup();
		}
	}
	
	/**
	 * Initialise the game
	 * @throws Exception if init fails
	 */
	private static void init() throws Exception {
		// Create a fullscreen window with 1:1 orthographic 2D projection, and with
		// mouse, keyboard, and gamepad inputs.
		Window.create(GAME_TITLE);
		
		// Enable vsync if we can
		Window.setVSyncEnabled(true);
		
		// Start up the sound system
		AL.create();
		
		// TODO: Load in your textures etc here
	}
	
	/**
	 * Runs the game (the "main loop")
	 */
	private static void run() {
		while (!finished) {
			// Always call Window.update(), all the time
			Window.update();
			
			if (Window.isCloseRequested()) {
				// Check for O/S close requests
				finished = true;
			} else if (Window.isActive()) {
				// The window is in the foreground, so we should play the game
				long timeThen = Sys.getTime();
				logic();
				render();
				// Stabilise the framerate if we haven't got vsync
				if (!Window.isVSyncEnabled()) {
					long timeNow = Sys.getTime();
					while ((float) (timeNow - timeThen) / (float) Sys.getTimerResolution() < FRAMETIME) {
						// This is a system-friendly way of allowing other stuff to use CPU if it wants to
						Thread.yield();
						timeNow = Sys.getTime();
					}
				}				
			} else {
				// The window is not in the foreground, so we can allow other stuff to run and
				// infrequently update
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				logic();
				if (Window.isVisible() || Window.isDirty()) {
					// Only bother rendering if the window is visible or dirty
					render();
				}
			}
		}
	}
	
	/**
	 * Do any game-specific cleanup
	 */
	private static void cleanup() {
		// TODO: save anything you want to disk here

		// Stop the sound
		AL.destroy();
		
		// Close the window
		Window.destroy();
	}
	
	/**
	 * Do all calculations, handle input, etc.
	 */
	private static void logic() {
		// Example input handler: we'll check for the ESC key and finish the game instantly when it's pressed
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			finished = true;
		}
		// TODO: all your game logic goes here.
	}
	
	/**
	 * Render the current frame
	 */
	private static void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		// TODO: all your rendering goes here
	}

}