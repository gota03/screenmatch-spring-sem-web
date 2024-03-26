package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		temporadas.forEach(System.out::println);
        for (int i = 0; i < dadosSerie.temporadas() ; i++) {
            List<DadosEpisodios> episodios = temporadas.get(i).episodios();
            for (int j = 0; j < episodios.size(); j++) {
                System.out.println(episodios.get(j).titulo());
            }
        }
    }
}
