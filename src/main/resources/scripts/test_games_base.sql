use fpldata_dev;

select sum(minutesplayed)-9132
from gamestats gs, seasonteamplayer stp, seasonteam st, season s, player p, team t
where p.name = 'Mohamed Salah'
and stp.player_id = p.id
and stp.seasonteam_id = st.id
and st.team_id = t.id
and t.name = 'Liverpool'
and s.startyear in (2017,2018,2019)
and s.id = st.season_id
and gs.seasonteamplayer_id = stp.id;

select sum(minutesplayed)-336
from gamestats gs, seasonteamplayer stp, seasonteam st, season s, player p, team t
where p.name = 'Danny Ings'
and stp.player_id = p.id
and stp.seasonteam_id = st.id
and st.team_id = t.id
and t.name = 'Liverpool'
and s.startyear in (2015)
and s.id = st.season_id
and gs.seasonteamplayer_id = stp.id;


select sum(minutesplayed)-5129
from gamestats gs, seasonteamplayer stp, player p
where p.name = 'Danny Ings'
and stp.player_id = p.id
and gs.seasonteamplayer_id = stp.id;

select sum(minutesplayed)-2311
from gamestats gs, seasonteamplayer stp, seasonteam st, season s, player p, team t
where p.name = 'Harry Maguire'
and stp.player_id = p.id
and stp.seasonteam_id = st.id
and st.team_id = t.id
and t.name = 'Hull'
and s.startyear in (2016)
and s.id = st.season_id
and gs.seasonteamplayer_id = stp.id;

select round(sum(xG) - 0.058)
from gamestats gs, seasonteamplayer stp, player p
where p.name = 'Tom Heaton'
and stp.player_id = p.id
and gs.seasonteamplayer_id = stp.id;

select round(sum(xA) - 10.45)
from gamestats gs, seasonteamplayer stp, player p
where p.name = 'Gabriel Fernando de Jesus'
and stp.player_id = p.id
and gs.seasonteamplayer_id = stp.id;
