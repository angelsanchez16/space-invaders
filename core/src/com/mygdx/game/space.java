package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class space implements Screen {
  	final Drop game;

	Texture enemigoImage;
	Texture jugadorImage;
        Texture meteoritoImage;
        Texture curaImage;
        Texture fondo;
        Texture disparojugadorImage;
        Texture disparoenemigoImage;
	Sound explosion;
        Sound cura;
        Sound disparoSound;
        Sound disparoSound1;
	Music epicMusic;
	OrthographicCamera camera;
	Rectangle jugador;
        Rectangle enemigo;  
	Array<Rectangle> disparosenemigos;
        Array<Rectangle> disparosjugador;
        Array<Rectangle> meteoritos;
        Array<Rectangle> vidas;
        
	long lastDropTime;
        long lastDropMetoro;
        long lastshootTime;
        long lastDropVida;
        int navesderrotadas;
        int vidasjugador=3;
        int velocidad=100;
        float posicion1;
        
	public space(final Drop game) {
		this.game = game;
            
		enemigoImage = new Texture(Gdx.files.internal("enemigo.png"));
		jugadorImage = new Texture(Gdx.files.internal("nave.png"));
                fondo=new Texture(Gdx.files.internal("fondo2.jpg"));
                disparojugadorImage = new Texture(Gdx.files.internal("laser.png"));
                disparoenemigoImage = new Texture(Gdx.files.internal("laser2.png"));
                meteoritoImage=new Texture(Gdx.files.internal("meteorito.png"));
                curaImage=new Texture(Gdx.files.internal("pill_green.png"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("explo.mp3"));
                cura = Gdx.audio.newSound(Gdx.files.internal("cura.mp3"));
                disparoSound = Gdx.audio.newSound(Gdx.files.internal("DisparoLaser.mp3"));
                disparoSound1 = Gdx.audio.newSound(Gdx.files.internal("DisparoLaser1.mp3"));
		epicMusic = Gdx.audio.newMusic(Gdx.files.internal("epic.mp3"));
		epicMusic.setLooping(true);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

                //recatnagulo del jugador
		jugador = new Rectangle();
		jugador.x = 800 / 2 - 99 / 2;
		jugador.y = 20;		
		jugador.width = 98;
		jugador.height = 75;

                //rectangulo del enemigo
                enemigo = new Rectangle();
		enemigo.x = 800 / 2 - 82; 
		enemigo.y = 399; 			
		enemigo.width = 93;
		enemigo.height = 84;
                
                

		// create the raindrops array
		disparosenemigos = new Array<Rectangle>();
                disparosjugador = new Array<Rectangle>();
                meteoritos = new Array<Rectangle>();
                vidas=new Array<Rectangle>();

        }
               
            //crea el disparo del enemigo
	private void spawndisparo() {                
		Rectangle laser = new Rectangle();
                System.out.println("Disparo x" + enemigo.x);            
		laser.x = enemigo.x + 46; 
		laser.y = 400;
		laser.width = 14;
		laser.height = 31;
		disparosenemigos.add(laser);
		lastDropTime = TimeUtils.nanoTime();
	}
            //crea el disparo del jugador
        private void spawnlaser() {
                Rectangle laser1 = new Rectangle();
                posicion1=jugador.x;
		laser1.x = posicion1;
		laser1.y = 55;
		laser1.width = 9;
		laser1.height = 37;
		disparosjugador.add(laser1);
		lastshootTime = TimeUtils.nanoTime();
	}
            //crea el meteorito
        private void spawnmeteoro() {
                Rectangle meteoro = new Rectangle();
		meteoro.x = MathUtils.random(0,800-48);
		meteoro.y = 400 ;
		meteoro.width = 43;
		meteoro.height = 43;
		meteoritos.add(meteoro);
		lastDropMetoro = TimeUtils.nanoTime();
	}
        
        //crea la cura
        private void spawnvida() {
                Rectangle vida = new Rectangle();
		vida.x = MathUtils.random(0,800-48);
		vida.y = 400 ;
		vida.width = 22;
		vida.height = 21;
		vidas.add(vida);
		lastDropVida = TimeUtils.nanoTime();
	}
        
        private void movimientoautomatico(){
            enemigo.x+=velocidad*Gdx.graphics.getDeltaTime();
            
            if(enemigo.x > 800-84 || enemigo.x<0){
                velocidad=-velocidad;
            }
        }
        
	@Override
	public void render(float delta) {
            
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                game.batch.setProjectionMatrix(camera.combined);
                game.batch.begin();
                game.batch.draw(fondo,0,0);     
		

                //dibuja el contador,las vidas,el enimigo y el jugador

		game.font.draw(game.batch, "enemigos destruidos: " + navesderrotadas, 0, 480);
                game.font.draw(game.batch, "vidas: " + vidasjugador, 700, 20);
                game.batch.draw(jugadorImage, jugador.x, jugador.y);
                game.batch.draw(enemigoImage, enemigo.x, enemigo.y);
                
                //dibuja el movimiento ascendente del disparo del jugador
                for (Rectangle raindrop1 : disparosjugador) {
                        game.batch.draw(disparojugadorImage,posicion1+98/2-5,raindrop1.y);
                }
                
                //dibuja el movimiento descendente del disparo enemigo
		for (Rectangle disparoenemigo : disparosenemigos) {
			game.batch.draw(disparoenemigoImage,disparoenemigo.x,disparoenemigo.y);
		}
               //dibuja el movimiento descendente del meteorito
		for (Rectangle meteoro : meteoritos) {
			game.batch.draw(meteoritoImage,meteoro.x,meteoro.y);
		}
                //dibuja el movimiento descendente de la cura
		for (Rectangle vida : vidas) {
			game.batch.draw(curaImage,vida.x,vida.y);
		}
		game.batch.end();
                camera.update();
                
		// recoge los datos de entrada para el jugador
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			jugador.x = touchPos.x - 98 / 2;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) jugador.x -= 650 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))	jugador.x += 650 * Gdx.graphics.getDeltaTime();
                if (Gdx.input.isKeyPressed(Keys.UP)){
                    if (TimeUtils.nanoTime() - lastshootTime > 1000000000){
                        spawnlaser();
                    }
                   }

		// limita el movimiento del jugador para que no se salga por los lados
		if (jugador.x < 0)
			jugador.x = 0;
		if (jugador.x > 800 - 98)
			jugador.x = 800 - 98;

		// calcula el tiempo para que el enemigo dispare
		if (TimeUtils.nanoTime() - lastDropTime> 2000000000){
			spawndisparo();
                }
                
                // calcula el tiempo para que caiga el meteorito
                if (TimeUtils.nanoTime() -  lastDropMetoro> 1500000000){
			spawnmeteoro();
                        lastDropMetoro=TimeUtils.nanoTime(); 
                }
                
                // calcula el tiempo para que caiga la cura
                if (TimeUtils.nanoTime() -  lastDropVida >= (2100000000)){
			spawnvida();
                        lastDropVida=TimeUtils.nanoTime(); 
                }
                
                movimientoautomatico();
                
                //crea el disparo enemigo
		Iterator<Rectangle> iter = disparosenemigos.iterator();                
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 300 * Gdx.graphics.getDeltaTime();
                        if (raindrop.overlaps(jugador)) {
				disparoSound.play();
                                iter.remove();
                                vidasjugador--;
			}
		}
                if (vidasjugador<=0){                        
                    this.game.setScreen(new PantallaInicio(game));
                    dispose();
                 }
                
                //crea el disparo aliado
                Iterator<Rectangle> iter1 = disparosjugador.iterator();
                
                while (iter1.hasNext()) {
			Rectangle raindrop1 = iter1.next();
                        raindrop1.y += 400 * Gdx.graphics.getDeltaTime();
                        
			if (raindrop1.overlaps(enemigo)) {
                                disparoSound1.play();
                                iter1.remove();
                                navesderrotadas++;
			}
                        
		}
                
                //crea el meteorito
                Iterator<Rectangle> iter2 = meteoritos.iterator();
                
                while (iter2.hasNext()) {
			Rectangle raindrop2 = iter2.next();
                        raindrop2.y -= 200 * Gdx.graphics.getDeltaTime();
                        
			if (raindrop2.overlaps(jugador)) {
                                explosion.play();
                                iter2.remove();
                                vidasjugador--;
                                vidasjugador--;
                                vidasjugador--;
			}
		}
                
                //crea la cura
                Iterator<Rectangle> iter3 = vidas.iterator();
                
                while (iter3.hasNext()) {
			Rectangle raindrop3 = iter3.next();
                        raindrop3.y -= 100 * Gdx.graphics.getDeltaTime();
                        
			if (raindrop3.overlaps(jugador)) {
                                cura.play();
                                iter3.remove();
                                vidasjugador++;
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		epicMusic.play();
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
            enemigoImage.dispose();
            jugadorImage.dispose();
            meteoritoImage.dispose();
            curaImage.dispose();
            fondo.dispose();
            disparojugadorImage.dispose();
            disparoenemigoImage.dispose();
            explosion.dispose();
            cura.dispose();
            disparoSound.dispose();
            disparoSound1.dispose();
            epicMusic.dispose();
	}

}