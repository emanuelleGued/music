package com.example.musica.principal;

import com.example.musica.model.Artista;
import com.example.musica.model.Musicas;
import com.example.musica.model.TipoArtistas;
import com.example.musica.repository.ArtistaRepository;
import com.example.musica.service.ConsultaChatGPT;
import com.example.musica.service.ConsumoApi;
import com.example.musica.service.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConverteDados conversor = new ConverteDados();
    private ArtistaRepository repositorio;

    public Main(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    //    public Main(MusicaRepository repositorio) {
//        this.repositorio = repositorio;
//    }
    public void menu(){
        var op = -1;
        while (op != 9){
            var menu = """
                    *** Screan Sound Musicas ***
                    
                    1 - Cadastrar artistas
                    2 - Cadastrar músicas
                    3 - Listar músicas
                    4 - Buscar músicas por artistas
                    5 - Pesquisar dados sobre artista
                    
                    9 - Sair 
                    """;
            System.out.println(menu);
            op = sc.nextInt();
            sc.nextLine();

            switch (op){
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusica();
                    break;
                case 3:
                    listarMusica();
                    break;
                case 4:
                    buscarMusicaPorArtistas();
                    break;
                case 5:
                    pesquisarDadosSobreArtistas();
                    break;
                case 9:
                    System.out.println("Encerrando aplicação ...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarArtistas() {
        var cadastrarNovo = "S";
        while (cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("Informe o nome do artista: ");
            var nome = sc.nextLine();
            System.out.println("Informe o tipo desse artista: (solo, dupla ou banda)");
            var tipo = sc.nextLine();
            TipoArtistas tipoArtistas = TipoArtistas.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtistas);
            repositorio.save(artista);
            System.out.println("Cadastrar novo artista? (S/N)");
            cadastrarNovo = sc.nextLine();
        }

    }

    private void cadastrarMusica() {
        System.out.println("Cadastrar música de que artista? ");
        var nome = sc.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);
        if (artista.isPresent()){
            System.out.println("Informe o título da música: ");
            var nomeMusica = sc.nextLine();
            Musicas musica = new Musicas(nomeMusica);
            musica.setArtista(artista.get()); // fazendo vinculo de musica com artista
            artista.get().getMusicas().add(musica); // adicionar no bd
            repositorio.save(artista.get());
        } else {
            System.out.println("Artista não encotrado");
        }

    }

    private void listarMusica() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void buscarMusicaPorArtistas() {
        System.out.println("Informe o nome da artista: ");
        var nome = sc.nextLine();

        List<Musicas> musicas = repositorio.buscaMusicaPorArtistas(nome);
        musicas.forEach(System.out::println);
    }

    private void pesquisarDadosSobreArtistas() {
        System.out.println("Artista muito bom!");
        System.out.println("Informe o nome da artista que voce quer pesquisar: ");
        var nome = sc.nextLine();

        var resposta = ConsultaChatGPT.obterInformacao(nome);
        System.out.println(resposta.trim());
    }
}
