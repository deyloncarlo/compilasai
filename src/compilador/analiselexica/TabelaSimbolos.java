/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador.analiselexica;

import java.util.ArrayList;
import java.util.List;

public class TabelaSimbolos
{

	List<RegistroTabelaSimbolo> listaRegistro = new ArrayList<>();

	public RegistroTabelaSimbolo getRegistro(String p_lexema)
	{
		RegistroTabelaSimbolo v_registroEncontrado = null;
		for (RegistroTabelaSimbolo v_registro : this.listaRegistro)
		{
			if (v_registro.getLexema().equals(p_lexema))
			{
				v_registroEncontrado = v_registro;
			}
		}
		return v_registroEncontrado;
	}

	public RegistroTabelaSimbolo insereNovoRegistro(Token p_token, String p_lexema)
	{
		RegistroTabelaSimbolo v_novoRegistro = new RegistroTabelaSimbolo(p_token, p_lexema);
		this.listaRegistro.add(v_novoRegistro);
		return v_novoRegistro;
	}
}
