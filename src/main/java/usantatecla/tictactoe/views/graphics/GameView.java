package usantatecla.tictactoe.views.graphics;

import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import usantatecla.tictactoe.models.Coordinate;
import usantatecla.tictactoe.models.Game;
import usantatecla.tictactoe.models.Player;
import usantatecla.tictactoe.models.PlayerType;
import usantatecla.tictactoe.models.Token;
import usantatecla.tictactoe.views.MessageView;
import usantatecla.tictactoe.views.PlayerView;
import usantatecla.tictactoe.views.TokenView;

@SuppressWarnings("serial")
class GameView extends JFrame {

	private static final String GAME_OVER = "Game Over";

	private Game game;

	GameView(Game game) {
		super(MessageView.START_GAME.getMessage());
		this.game = game;
		this.getContentPane().setLayout(new GridBagLayout());
		this.setSize(400, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	void start() {
		ChoosePlayersView choosePlayersView = new ChoosePlayersView(this.getRootPane());
		this.getContentPane().add(choosePlayersView, new Constraints(0, 0, 3, 1));
		this.setVisible(true);
		do {
			System.out.println("");
			if (choosePlayersView.getPlayersNumber() != null) {
				int numberPlayers = Integer.parseInt(choosePlayersView.getPlayersNumber());
				this.game.createPlayers(numberPlayers);
			}
		} while (choosePlayersView.getPlayersNumber() == null);
	}

	boolean play() {
		this.getContentPane().removeAll();
		Player player = this.game.getTokenPlayerFromTurn();
		this.getContentPane().add(new BoardView(this.game.getBoard()), new Constraints(0, 0, 1, 1));
		if (!this.game.isBoardComplete()) {
			PlayerView playerView = player.getType() == PlayerType.USER_PLAYER
					? new UserPlayerView(player, false, this.getContentPane())
					: new MachinePlayerView(player);
			this.setVisible(true);
			Coordinate coordinate = playerView.readCoordinateToPut();
			this.game.putTokenPlayerFromTurn(coordinate);
		} else {
			PlayerView playerView = player.getType() == PlayerType.USER_PLAYER
					? new UserPlayerView(player, true, this.getContentPane())
					: new MachinePlayerView(player);
			this.setVisible(true);
			Coordinate[] coordinates = playerView.readCoordinatesToMove();
			this.game.moveTokenPlayerFromTurn(coordinates);
		}
		this.game.changeTurn();
		return this.game.isTicTacToe();
	}

	void result() {
		this.getContentPane().removeAll();
		this.getContentPane().add(new BoardView(this.game.getBoard()), new Constraints(0, 0, 1, 1));
		this.setVisible(true);
		int otherValue = this.game.getOtherValueFromTurn();
		String message = new TokenView(Token.values()[otherValue]).getToken() + " "
				+ MessageView.PLAYER_WIN.getMessage();
		JOptionPane.showMessageDialog(null, message, GameView.GAME_OVER, JOptionPane.WARNING_MESSAGE);
	}

}