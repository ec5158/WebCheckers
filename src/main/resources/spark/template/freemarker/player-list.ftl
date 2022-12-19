<font color = "blue"> 
    <h2>Players Online</h2>
</font>

<ul>
    <#if currentUser??>
        <#if players??>
            <#list players as player>
                <li><b>${player.name}</b> <a href="/game?name=${player.name}">Challenge</a></li>
            <#else>
                <li><i>There are no other players available to play at this time.</i></li>
            </#list>
        <#else>
                <li><i>There are no other players available to play at this time.</i></li>
        </#if>
    <#else>
        <li>${numPlayersMessage}</li>
    </#if>
</ul>