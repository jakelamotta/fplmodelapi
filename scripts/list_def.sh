mysql -ppassword -uroot -P password <<EOF

use fpl;

select player, (xpabs-0.8687889144630985)/cost as c , team, cost
from calculatedplayerstatistics cps
where cps.position like "D%"
and cps.minutesplayed > 180
and cost < $2
order by c desc
limit $1;

EOF
