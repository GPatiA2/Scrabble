package Servidor.InterpreterFactoria;

import Servidor.Interprete.Interpreter;
import Servidor.InterpreterFactoria.InterpreterFactory.Estado;

public interface InterpreterFactoryInterface {
	public Interpreter buildInterpreter(Estado e);
}
