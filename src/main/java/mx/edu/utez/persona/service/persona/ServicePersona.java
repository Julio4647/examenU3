package mx.edu.utez.persona.service.persona;

import mx.edu.utez.persona.model.persona.BeanPersona;
import mx.edu.utez.persona.model.persona.DaoPersona;
import mx.edu.utez.persona.utils.ResultAction;

import java.util.List;

public class ServicePersona {
    DaoPersona daoPersona = new DaoPersona();
    public List<BeanPersona> findAll(){return daoPersona.findAll();}

    public ResultAction save (BeanPersona persona){
        ResultAction result = new ResultAction();
        if (daoPersona.add(persona)){
            result.setResult(true);
            result.setMessage("Persona registrada correctamente");
            result.setStatus(200);
        }else {
            result.setResult(false);
            result.setMessage("Ocurrió un error al registrar");
            result.setStatus(400);
        }
        return result;
    }

public BeanPersona getPersona(int id){
        return daoPersona.findOne(id);
}
    public ResultAction update(BeanPersona persona){
        ResultAction result = new ResultAction();
        if (daoPersona.update(persona)){
            System.out.println(persona.getName());
            System.out.println(persona.getSurname());
            System.out.println(persona.getBirthday());
            result.setStatus(200);
            result.setResult(false);
            result.setMessage("Persona actualizado correctamente");
        }else{
            result.setStatus(400);
            result.setResult(true);
            result.setMessage("Ocurrió un error");
        }
        return result;
    }

    public ResultAction delete(String id){
        ResultAction result = new ResultAction();
        try{
            if (daoPersona.delete(Integer.parseInt(id))){
                result.setStatus(200);
                result.setResult(false);
                result.setMessage("Persona eliminada correctamente");
            }else{
                result.setStatus(400);
                result.setResult(true);
                result.setMessage("Ocurrió un error");
            }
        }catch (NumberFormatException e){
            result.setStatus(400);
            result.setResult(true);
            result.setMessage("Ocurrió un error");
        }
        return result;
    }
}
