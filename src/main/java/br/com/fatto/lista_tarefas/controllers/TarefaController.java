package br.com.fatto.lista_tarefas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatto.lista_tarefas.models.Tarefa;
import br.com.fatto.lista_tarefas.repositories.TarefaRepository;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/")
public class TarefaController {
    
    private final TarefaRepository tarefaRepository;

    //Injeção de dependência
    public TarefaController(TarefaRepository tarefaRepository){
        this.tarefaRepository = tarefaRepository;
    }

    @GetMapping("/")
    public ModelAndView list(){
        var modelAndView = new ModelAndView("tarefas/list");
        modelAndView.addObject("tarefas", tarefaRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create(){
        var modelAndView = new ModelAndView("tarefas/form");
        modelAndView.addObject("tarefa", new Tarefa());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@Valid Tarefa tarefa, BindingResult result){
        if(result.hasErrors()){
            return "tarefas/form";
        }
        tarefaRepository.save(tarefa);
        return "redirect:/";
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id){
        var tarefa = tarefaRepository.findById(id);
        if(tarefa.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        var modelAndView = new ModelAndView("tarefas/form");
        modelAndView.addObject("tarefa", tarefa.get());
        
        return modelAndView;
    }
    @PostMapping("/edit/{id}")
    public String edit(@Valid Tarefa tarefa, BindingResult result){
        if(result.hasErrors()){
            return "tarefas/form";
        }
        tarefaRepository.save(tarefa);
        return "redirect:/";
    }
    
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id){
        var tarefa = tarefaRepository.findById(id);
        if(tarefa.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        var modelAndView = new ModelAndView("tarefas/delete");
        modelAndView.addObject("tarefa", tarefa.get());
        
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Tarefa tarefa){
        tarefaRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/finish/{id}")
    public String finish(@PathVariable Long id){
        var optionalTarefa = tarefaRepository.findById(id);
        if(optionalTarefa.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        var tarefa = optionalTarefa.get();
        tarefa.markHasFinished();
        tarefaRepository.save(tarefa);
        return "redirect:/";
    }
}
