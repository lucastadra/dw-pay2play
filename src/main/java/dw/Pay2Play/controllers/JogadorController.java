package dw.Pay2Play.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dw.Pay2Play.models.Jogador;
import dw.Pay2Play.repositories.JogadorRepository;

@RestController
@RequestMapping("api")

public class JogadorController {

    @Autowired
    JogadorRepository rep;

    // Método GET /api/jogadores -- lista os jogadores
    @GetMapping("/jogadores")
    public ResponseEntity<List<Jogador>> getAllJogadores(@RequestParam(required = false) String nome) {

        try {
            List<Jogador> lj = new ArrayList<Jogador>();

            if (nome == null){
                rep.findAll().forEach(lj::add);

            } else {
                rep.findByNomeContaining(nome).forEach(lj::add);
            }

            if (lj.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lj, HttpStatus.OK);

        } catch (Exception e){
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método GET /api/jogadores -- lista jogador especifico
    @GetMapping("/jogadores/{cod_jogador}")
    public ResponseEntity<Jogador> getJogadorById(@PathVariable("cod_jogador") int cod_jogador) {

        try {

            Optional<Jogador> jogador = rep.findById(cod_jogador);
            
            if (jogador.isPresent())
                return new ResponseEntity<>(jogador.get(), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e){
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método POST /api/jogadores -- cria um novo jogador
    @PostMapping("/jogadores")
    public ResponseEntity<Jogador> createJogador(@RequestBody Jogador jg) {

        try {
            System.out.println(jg);
            Jogador j = rep.save(new Jogador(jg.getNome(), jg.getEmail(), jg.getDataNasc()));


            return new ResponseEntity<>(j, HttpStatus.CREATED);

        } catch (Exception e){
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método PUT /api/jogadores/id -- edita um jogador
    @PutMapping("/jogadores/{cod_jogador}")
    public ResponseEntity<Jogador> updateJogador(@PathVariable("cod_jogador") int cod_jogador, @RequestBody Jogador j)
    {
        Optional<Jogador> data = rep.findById(cod_jogador);

        if (data.isPresent())
        {
            Jogador jg = data.get();
            jg.setNome(j.getNome().isEmpty() ? jg.getNome() : j.getNome());
            jg.setEmail(j.getEmail().isEmpty() ? jg.getEmail() : j.getEmail());
            jg.setDataNasc(j.getDataNasc().toString().isEmpty() ? jg.getDataNasc() : j.getDataNasc());

            return new ResponseEntity<>(rep.save(jg), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    // Método DELETE /api/jogadores -- deletr um jogador
    @DeleteMapping("/jogadores/{cod_jogador}")
    public ResponseEntity<HttpStatus> deleteJogador(@PathVariable("cod_jogador") int cod_jogador) {
        try {
            rep.deleteById(cod_jogador);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            //TODO: handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    // Método DELETE /api/jogadores -- deleta todos
    @DeleteMapping("/jogadores")
    public ResponseEntity<HttpStatus> deleteAllJogadores() {
        try {
            rep.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            //TODO: handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}