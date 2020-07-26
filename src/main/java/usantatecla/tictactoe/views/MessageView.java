package usantatecla.tictactoe.views;

import usantatecla.utils.Console;

enum MessageView {
	LINE_BREAK(""),
    EMPTY("-"), 
    SEPARATOR("-----------------------------------------------------"),
    VERTICAL_LINE_CENTERED(" | "),
	VERTICAL_LINE_LEFT("| "),
	PLAYER_WIN(" Player: You win!!! :-)"),
	START_GAME("-------------------- TIC TAC TOE --------------------");

	private String message;
	
	private Console console;

	private MessageView(String message) {
		this.console = new Console();
		this.message = message;
	}

	void write() {
		this.console.write(this.message);
	}

	void writeln() {
		this.console.writeln(this.message);
	}

}