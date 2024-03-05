<h2>Guia de Utilização - Back-End Infosite</h2>
<p></p>

<h3>Autenticação</h3>
<p>Para realizar a atuenticação no sistema, é preciso realizar uma requisição POST para <code>/login</code> enviando o corpo da requisição no formato:<code>{"login":"loginDoUsuario","senha":"senhaDoUsuario"}</code></p>
<p>Caso os dados de autenticação estejam corretos será retornado um JSON com as seguintes informações:</p>
  <ul>
    <li>Nome</li>
    <li>Imagem</li>
    <li>Token</li>
  </ul>
  <p>O token deverá ser enviado no cabeçalho <code>Authentication</code> para realizar quaisquer outras requisições, com exceção de POST para /contato</p>
<h3>Endpoints</h3>
<p>
  <b>Formulários de contato:</b>
  <br/>
  <ul>
    <li>GET<code>api/contato</code>: Lista todos os formulários registrados</li>
    <li>GET<code>api/contato/{email}</code>: Lista todos os formulários registrados filtrados pelo {email}</li>
    <li>POST<code>api/contato</code>:Realiza o envio dos e-mails interno e cliente e armazena os dados do formulário de contato no banco de dados. O JSON  enviado do formulário deve possuir os seguintes campos: (nome,email,telefone,assunto)</li>
    <li>DEL<code>api/contato/{id}</code>: Exclui do banco de dados um formulário de contato baseado no {id} enviado</li>
  </ul>
</p>
<p>
  <b>Usuários:</b>
  <ul>
    <li>GET<code>api/usuarios</code>: Lista todos os usuários registrados</li>
    <li>GET<code>api/usuarios/{id}</code>: Busca um usuário pelo id</li>
    <li>POST<code>api/usuarios</code>: Cadastra um usuário no banco de dados. O JSON enviado deve conter os seguintes campos: (nome,login,senha,imagem)</li>
    <li>PUT<code>api/usuarios/{id}</code>: Edita o usuário com id passado, deve ser enviado no corpo da requisição os mesmos campos do cadastro</li>
    <li>DEL<code>api/usuarios/{id}</code>: Exclui do banco de dados o usuário de id enviado</li>
  </ul>
</p>
<p>
  <b>Projetos:</b>
  <ul>
    <li>GET<code>api/projetos</code>: Lista todos os projetos registrados</li>
    <li>GET<code>api/projetos/{id}</code>: Busca um projeto pelo id</li>
    <li>POST<code>api/projetos</code>: Cadastra um projeto no banco de dados. O JSON enviado deve conter os seguintes campos: (titulo,descricao,imagem,link,ativo). Sendo que o campo ativo é um booleano(Verdadeiro ou Falso)</li>
    <li>PUT<code>api/projetos/{id}</code>: Edita o projeto com id passado, deve ser enviado no corpo da requisição os mesmos campos do cadastro</li>
    <li>DEL<code>api/projetos/{id}</code>: Exclui do banco de dados o projeto de id enviado</li>
  </ul>
  <p>Observação: Os campos de imagem devem ser enviados como um Array de Bits para o correto armazenamento no banco de dados</p>
</p>
