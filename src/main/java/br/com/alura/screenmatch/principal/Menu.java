package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=9508a9c7";
    public void exibeMenu(){

        System.out.print("Digite o nome da série: ");
        String serie = sc.next();
        String json = consumoApi.obterDados(ENDERECO+serie.replace(" ", "+")+API_KEY);
        DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dadosSerie.temporadas(); i++) {
			json = consumoApi.obterDados(ENDERECO+serie.replace(" ", "+")+"&season="+i+API_KEY);
			DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
        System.out.println();
		temporadas.forEach(System.out::println);

        System.out.println();
        System.out.println("Top 5 melhores episódios");

        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .toList();

//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Depois do filtro" + e))
//                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
//                .peek(e -> System.out.println("Depois de ordenar decrescente" + e))
//                .limit(5)
//                .peek(e -> System.out.println("Depois de limitar em 5" + e))
//                .forEach(System.out::println);


        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Informe o nome do trecho do episódio que deseja");
        String trechoTituloEpisodio = sc.next();

        Optional<Episodio> episodioBuscado = episodios.stream()
                        .filter(e -> e.getTitulo()
                                .toUpperCase()
                                .contains(trechoTituloEpisodio.toUpperCase()))
                                .findFirst();

        if(episodioBuscado.isPresent()){
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada() + "\n" +
                                "Número do episódio: " + episodioBuscado.get().getNumeroEpisodio());
        }else{
            System.out.println("Episódio não encontrado");
        }

//        System.out.print("Informe um ano para buscar os episódios: ");
//        int ano = sc.nextInt();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "\n"+"Temporada: " + e.getTemporada() + "\n" +
//                                "Episódio: " + e.getTitulo() + "\n" +
//                                "Data lançamento: " + e.getDataLancamento().format(formatter)
//                ));
    }
}
