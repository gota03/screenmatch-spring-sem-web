package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Episodio(Integer numeroTemporada, DadosEpisodios dadosEpisodios) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodios.titulo();
        this.numeroEpisodio = dadosEpisodios.numero();
        try{
            this.avaliacao = Double.valueOf(dadosEpisodios.avaliacao());
        }catch (NumberFormatException exception){
            this.avaliacao = 0.0;
        }
        try{
            this.dataLancamento = LocalDate.parse(dadosEpisodios.dataLancamento());
        }catch (DateTimeParseException exception){
            this.dataLancamento = null;
        }
    }
    public LocalDate getDataLancamento() {
        return dataLancamento;
    }
    public Integer getTemporada() {
        return temporada;
    }
    public String getTitulo() {
        return titulo;
    }
    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }
    @Override
    public String toString() {
        return "\n" + "temporada = " + temporada + "\n"+
                "titulo = " + titulo + "\n"+
                "numeroEpisodio = " + numeroEpisodio + "\n"+
                "avaliacao = " + avaliacao + "\n"+
                "dataLancamento = " + dataLancamento;
    }
}
