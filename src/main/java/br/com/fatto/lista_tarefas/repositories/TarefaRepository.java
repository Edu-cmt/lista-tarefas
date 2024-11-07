package br.com.fatto.lista_tarefas.repositories;


import org.springframework.data.jpa.repository.JpaRepository;


import br.com.fatto.lista_tarefas.models.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{

}
