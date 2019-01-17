package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class PantallaInicio implements Screen {
        final Drop game;
        Texture fondo;
	OrthographicCamera camera;

        
	public PantallaInicio(final Drop gam) {
		game = gam;
                fondo=new Texture(Gdx.files.internal("fondo2.jpg"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
                
	}

    PantallaInicio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
                game.batch.draw(fondo,0,0); 
		game.font.draw(game.batch, "Bienvenido a Space Invaders Falso!!! ", 300, 400);
		game.font.draw(game.batch, "Toca para empezar a matar !", 300, 200);                
		game.batch.end();

		if (Gdx.input.isTouched()) {
                    Drop game1 = null;
			game.setScreen(new space(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}