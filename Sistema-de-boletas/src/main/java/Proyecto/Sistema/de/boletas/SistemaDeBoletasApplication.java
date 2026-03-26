package Proyecto.Sistema.de.boletas;

import Proyecto.Sistema.de.boletas.Entidades.Evento;
import Proyecto.Sistema.de.boletas.Servicio.EventoServicio;
import Proyecto.Sistema.de.boletas.gui.VentanaPrincipal;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "Proyecto.Sistema.de.boletas")
public class SistemaDeBoletasApplication {

	private static ConfigurableApplicationContext applicationContext;
	private static final CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) {
		// Deshabilitar modo headless
		System.setProperty("java.awt.headless", "false");

		System.out.println("=== INICIANDO APLICACIÓN ===");

		// Iniciar Spring en un hilo separado
		Thread springThread = new Thread(() -> {
			try {
				SpringApplication app = new SpringApplication(SistemaDeBoletasApplication.class);
				app.setBannerMode(Banner.Mode.OFF);
				app.setHeadless(false);

				System.out.println("Iniciando Spring Boot...");
				applicationContext = app.run(args);

				System.out.println("✓ Spring Boot iniciado correctamente");
				System.out.println("✓ Beans cargados: " + applicationContext.getBeanDefinitionCount());

				// CARGAR EVENTOS DE EJEMPLO
				cargarEventosEjemplo();

				latch.countDown();
			} catch (Exception e) {
				System.err.println("✗ Error al iniciar Spring: " + e.getMessage());
				e.printStackTrace();
				latch.countDown();
			}
		});
		springThread.start();

		// Iniciar la GUI después de que Spring esté listo
		SwingUtilities.invokeLater(() -> {
			try {
				System.out.println("Esperando a que Spring termine de iniciar...");

				// Esperar hasta 15 segundos a que Spring inicie
				boolean springIniciado = latch.await(15, TimeUnit.SECONDS);

				if (springIniciado && applicationContext != null && applicationContext.isRunning()) {
					System.out.println("✓ Spring listo, iniciando GUI...");

					// Configurar Look and Feel del sistema
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (Exception e) {
						System.err.println("Error al configurar LookAndFeel: " + e.getMessage());
					}

					// Crear y mostrar ventana principal
					VentanaPrincipal ventana = new VentanaPrincipal();
					ventana.setVisible(true);
					ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					System.out.println("✓ Aplicación lista para usar");
				} else {
					System.err.println("✗ Tiempo de espera agotado o Spring no inició correctamente");
					JOptionPane.showMessageDialog(null,
							"No se pudo iniciar la aplicación.\n" +
									"Verifique la configuración de la base de datos H2.",
							"Error de inicio",
							JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			} catch (Exception e) {
				System.err.println("✗ Error al iniciar GUI: " + e.getMessage());
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"Error al iniciar la aplicación: " + e.getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		});
	}

	/**
	 * Método para cargar eventos de ejemplo si no existen
	 */
	private static void cargarEventosEjemplo() {
		try {
			EventoServicio eventoServicio = applicationContext.getBean(EventoServicio.class);

			// Verificar si ya hay eventos
			if (eventoServicio.listarEventos().isEmpty()) {
				System.out.println("=== CARGANDO EVENTOS DE EJEMPLO ===");

				// Evento 1: Concierto de Rock
				Evento evento1 = Evento.builder()
						.nombre("Concierto de Rock - Los Redondos")
						.fecha(LocalDate.of(2026, 5, 15))
						.hora(LocalTime.of(20, 0))
						.lugar("Estadio Nacional")
						.patrocinador("Rock & Roll S.A.")
						.totalBoletas(1000)
						.boletasZonaA(200)
						.boletasZonaB(300)
						.boletasZonaC(500)
						.build();
				eventoServicio.guardar(evento1);
				System.out.println("✓ Evento 1 creado: Concierto de Rock");

				// Evento 2: Fútbol - Final de Copa
				Evento evento2 = Evento.builder()
						.nombre("Final Copa Libertadores")
						.fecha(LocalDate.of(2026, 6, 10))
						.hora(LocalTime.of(15, 30))
						.lugar("Estadio Monumental")
						.patrocinador("Cerveza Andina")
						.totalBoletas(5000)
						.boletasZonaA(800)
						.boletasZonaB(1200)
						.boletasZonaC(3000)
						.build();
				eventoServicio.guardar(evento2);
				System.out.println("✓ Evento 2 creado: Final Copa Libertadores");

				// Evento 3: Teatro - Obra de comedia
				Evento evento3 = Evento.builder()
						.nombre("Obra de Teatro: 'El Último Show'")
						.fecha(LocalDate.of(2026, 7, 5))
						.hora(LocalTime.of(19, 0))
						.lugar("Teatro Municipal")
						.patrocinador("Fundación Cultural")
						.totalBoletas(300)
						.boletasZonaA(50)
						.boletasZonaB(100)
						.boletasZonaC(150)
						.build();
				eventoServicio.guardar(evento3);
				System.out.println("✓ Evento 3 creado: Obra de Teatro");

				System.out.println("=== EVENTOS DE EJEMPLO CARGADOS EXITOSAMENTE ===");
			} else {
				System.out.println("Ya existen eventos en la base de datos, no se cargan ejemplos");
			}
		} catch (Exception e) {
			System.err.println("Error al cargar eventos de ejemplo: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> beanClass) {
		if (applicationContext != null && applicationContext.isRunning()) {
			try {
				return applicationContext.getBean(beanClass);
			} catch (Exception e) {
				System.err.println("Error obteniendo bean " + beanClass.getName() + ": " + e.getMessage());
				return null;
			}
		}
		return null;
	}

	public static boolean isSpringReady() {
		return applicationContext != null && applicationContext.isRunning();
	}
}