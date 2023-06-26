# Library Manager
Tabalho apresentado a matéria de Desenvolvimento Backend para o curso de pós graduação em Desenvolvimento de Aplicativos Móveis da PUCPR

Professor: Vinicius Godoy

Entrega: 02/07/2023

## Integrantes do grupo
- Bruno Thuma
- Matheus Mei
- Raison Robert

## Objetivo
Sistema de gestão de emprestimos de livros de uma biblioteca.

Livros cadastrados podem ser emprestados por usuarios e sua devolução registrada por um funcionario.
## Regras dos casos de uso
- Apenas a rota de login e de criação de usuarios está disponivel sem um access token
- Apenas usuarios `role==STAFF` podem deletar itens da lista de livros de cada usuario
- Apenas usuarios `role==ADMIN` podem deletar usuarios
- Para um usuario ser deletado, deve existir pelo menos um usuarios `role==ADMIN` no sistema apos a operação
- Apenas usuarios `role==STAFF` podem cadastrar livros
- Usuarios logados podem adicionar livros a suas listas, caracterizando emprestimo
